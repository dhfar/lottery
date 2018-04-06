pragma solidity ^0.4.21;

import "./Owned.sol";

contract CKColonyMarketPlace is Owned {
    /*
        Контракты
    */
    CandyKillerAccount ckAccount;
    CandyKillerColony ckColony;
    /*
        Продажа земли
    */
    /*
        Предложение на продажу
    */
    struct OfferEarthCell {
        // ячейка
        uint index;
        // владелец ячейки
        address owner;
        // цена
        uint price;
        // продажа конкретному покупателю
        address specialCustomer;
        // доступен для продажи
        bool isForSale;
    }
    /*
        Предложение на покупку
    */
    struct BidEarthCell {
        // ячейка
        uint index;
        // покупатель
        address customer;
        // цена
        uint price;
    }
    /*
        Продажа колоний
    */
    /*
        Предложение на продажу
    */
    struct OfferColony {
        // колония
        uint index;
        // владелец ячейки
        address owner;
        // цена
        uint price;
        // продажа конкретному покупателю
        address specialCustomer;
        // доступен для продажи
        bool isForSale;
    }
    /*
        Предложение на покупку
    */
    struct BidColony {
        // колония
        uint index;
        // покупатель
        address customer;
        // цена
        uint price;
    }
    /*
        Модификаторы
    */
    modifier onlyActiveAccount {
        // Учетная запись создана и активна
        require(ckAccount.isCreateAndActive(msg.sender));
        _;
    }
    // Массив предложений продажи ячейки
    mapping(uint => OfferEarthCell) public earthCellOffer;
    // Массив ставок на продажу ячейки
    mapping(uint => BidEarthCell) public earthCellBids;
    // Массив предложений на продажу колонии
    mapping(uint => OfferColony) public colonyOffers;
    // Массив предложений на покупку колонии
    mapping(uint => BidColony) public colonyBids;
    /*
        События
    */
    event EarthCellBidEntered(uint cellIndex, uint price, address customerAddress);
    event EarthCellBidWithdrawn(uint cellIndex, uint price, address customerAddress);
    event EarthCellBidAccepted(uint charactecellIndexrIndex, uint price, address tenantAddress);
    event EarthCellOffered(uint cellIndex, uint price, address toAddress);
    event EarthCellOfferBuy(uint cellIndex, uint price, address toAddress);
    event WithdrawOfferForEarthCell(uint cellIndex);

    event ColonyBidEntered(uint colonyIndex, uint price, address customerAddress);
    event ColonyBidWithdrawn(uint colonyIndex, uint price, address customerAddress);
    event ColonyBidAccepted(uint charactecellIndexrIndex, uint price, address tenantAddress);
    event ColonyOffered(uint colonyIndex, uint price, address toAddress);
    event ColonyOfferBuy(uint colonyIndex, uint price, address toAddress);
    event WithdrawOfferForColony(uint colonyIndex);

    function CKColonyMarketPlace() public {
        owner = msg.sender;
    }

    /*
        инициализация объекта CandyKillerAccount
    */
    function initCandyKillerAccount(address accountContract) public onlyOwner {
        if (accountContract == 0x0) return;
        ckAccount = CandyKillerAccount(accountContract);
    }
    /*
        инициализация объекта CandyKillerColony
    */
    function initCandyKillerColony(address colonyContract) public onlyOwner {
        if (colonyContract == 0x0) return;
        ckColony = CandyKillerColony(colonyContract);
    }
    /*
        Добавление предложения на покупку ячейки земли
    */
    function enterBidEarthCell(uint cellIndex) public onlyActiveAccount payable {
        // ячейка свободна для продажи и принадлежит
        if (!ckColony.isExistAndForSaleCell(msg.sender, cellIndex)) return;
        BidEarthCell memory existBidEarthCell = earthCellBids[cellIndex];
        if (msg.value <= 0) return;
        // сумму предыдущего предложения запишем на счет пользователя
        if (msg.value > existBidEarthCell.price) {
            // вернем деньги
            ckAccount.addPendingWithdrawals.value(existBidEarthCell.price)(existBidEarthCell.customer);
            // создаем новое предложение
            earthCellBids[cellIndex] = BidEarthCell(cellIndex, msg.sender, msg.value);
            emit EarthCellBidEntered(cellIndex, msg.value, msg.sender);
        }
    }
    /*
        Продажа ячейки земли
    */
    function acceptBidEarthCell(uint cellIndex) public onlyActiveAccount {
        // ячейка свободна для продажи и принадлежит
        if (!ckColony.isOwnerEarthCell(msg.sender, cellIndex)) return;
        BidEarthCell memory existBid = earthCellBids[cellIndex];
        if (existBid.price == 0) return;
        // передаем ячейку новому владельцу
        if (!ckColony.transferEarthCell(msg.sender, existBid.customer, cellIndex)) return;
        earthCellBids[cellIndex] = BidEarthCell(cellIndex, 0x0, 0);
        // переведём бабки пользователю
        ckAccount.addPendingWithdrawals.value(existBid.price)(msg.sender);
        emit EarthCellBidAccepted(cellIndex, existBid.price, existBid.customer);
    }
    /*
        Снятие предложения на покупку ячейки земли
    */
    function withdrawBidEarthCell(uint cellIndex) public onlyActiveAccount {
        BidEarthCell memory existBidEarthCell = earthCellBids[cellIndex];
        // существует предложение
        if (existBidEarthCell.customer != msg.sender) return;
        // Обнуляем предложения по ячейке
        emit EarthCellBidWithdrawn(cellIndex, existBidEarthCell.price, msg.sender);
        // вернем деньги за предложение
        msg.sender.transfer(existBidEarthCell.price);
        earthCellBids[cellIndex] = BidEarthCell(cellIndex, 0x0, 0);
    }
    /*
        Добавление предложения на продажу ячейки земли
    */
    function offerEarthCell(uint cellIndex, uint price) public onlyActiveAccount {
        // ячейка свободна для продажи и принадлежит
        if (!ckColony.isExistAndForSaleCell(0x0, cellIndex)) return;
        // хозяин ячейки
        if (!ckColony.isOwnerEarthCell(msg.sender, cellIndex)) return;
        if (price == 0) return;
        earthCellOffer[cellIndex] = OfferEarthCell(cellIndex, msg.sender, price, 0x0, true);
        emit EarthCellOffered(cellIndex, price, 0x0);
    }
    /*
        Добавление предложения на продажу ячейки земли, конкретному пользователю
    */
    function offerEarthCellToAddress(uint cellIndex, uint price, address toAddress) public onlyActiveAccount {
        // ячейка свободна для продажи и принадлежит
        if (!ckColony.isExistAndForSaleCell(0x0, cellIndex)) return;
        // хозяин ячейки
        if (!ckColony.isOwnerEarthCell(msg.sender, cellIndex)) return;
        if (price == 0) return;
        if (toAddress == 0x0) return;
        earthCellOffer[cellIndex] = OfferEarthCell(cellIndex, msg.sender, price, toAddress, true);
        emit EarthCellOffered(cellIndex, price, toAddress);
    }
    /*
        Купить ячейку земли
    */
    function buyEarthCell(uint cellIndex) public onlyActiveAccount payable {
        // ячейка не принадлежит текущему юзеру
        if (ckColony.isOwnerEarthCell(msg.sender, cellIndex)) return;
        OfferEarthCell memory existOffer = earthCellOffer[cellIndex];
        if (!existOffer.isForSale) return;
        // если прислал мало денег
        if (msg.value < existOffer.price) return;
        // если указан конкретный покупатель
        if (existOffer.specialCustomer != 0x0 && existOffer.specialCustomer != msg.sender) return;
        // передаем ячейку новому владельцу
        if (!ckColony.transferEarthCell(existOffer.owner, msg.sender, cellIndex)) return;
        earthCellOffer[cellIndex] = OfferEarthCell(cellIndex, 0x0, 0, 0x0, false);
        // переведём бабки пользователю
        ckAccount.addPendingWithdrawals.value(existOffer.price)(msg.sender);
        emit EarthCellOfferBuy(cellIndex, existOffer.price, msg.sender);
        // проверим не предлагал свои условия покупатель
        BidEarthCell memory existBidEarthCell = earthCellBids[cellIndex];
        // существует предложение
        if (existBidEarthCell.customer == msg.sender) {
            // Обнуляем предложения по ячейке
            emit EarthCellBidWithdrawn(cellIndex, existBidEarthCell.price, msg.sender);
            // вернем деньги за предложение
            msg.sender.transfer(existBidEarthCell.price);
            earthCellBids[cellIndex] = BidEarthCell(cellIndex, 0x0, 0);
        }
    }
    /*
        Снятие предложения на продажу ячейки земли
    */
    function withdrawOfferEarthCell(uint cellIndex) public onlyActiveAccount {
        // ячейка свободна для продажи и принадлежит
        if (!ckColony.isOwnerEarthCell(msg.sender, cellIndex)) return;
        earthCellOffer[cellIndex] = OfferEarthCell(cellIndex, msg.sender, 0, 0x0, false);
        emit WithdrawOfferForEarthCell(cellIndex);
    }
    /*
        Получить информацию о предложение на продажу ячейки
    */
    function getOfferEarthCell(uint cellIndex) public view returns (uint, address, uint, address, bool) {
        return (
        earthCellOffer[cellIndex].index,
        earthCellOffer[cellIndex].owner,
        earthCellOffer[cellIndex].price,
        earthCellOffer[cellIndex].specialCustomer,
        earthCellOffer[cellIndex].isForSale
        );
    }
    /*
        Ячейка выставлена на продажу
    */
    function isExistOfferEarthCell(uint cellIndex) public view returns (bool) {
        return earthCellOffer[cellIndex].isForSale;
    }
    /*
        Получить информацию о предложение на покупку ячейки
    */
    function getBidEarthCell(uint cellIndex) public view returns (uint, address, uint) {
        return (
        earthCellBids[cellIndex].index,
        earthCellBids[cellIndex].customer,
        earthCellBids[cellIndex].price
        );
    }
    /*
        Добавление предложения на покупку колонии
    */
    function enterBidColony(uint colonyIndex) public onlyActiveAccount payable {
        // колония существует
        if (!ckColony.isExistColony(msg.sender, colonyIndex)) return;
        BidColony memory existBidColony = colonyBids[colonyIndex];
        if (msg.value <= 0) return;
        // сумму предыдущего предложения запишем на счет пользователя
        if (msg.value > existBidColony.price) {
            // вернем деньги
            ckAccount.addPendingWithdrawals.value(existBidColony.price)(existBidColony.customer);
            // создаем новое предложение
            colonyBids[colonyIndex] = BidColony(colonyIndex, msg.sender, msg.value);
            emit ColonyBidEntered(colonyIndex, msg.value, msg.sender);
        }
    }
    /*
        Продажа колонии
    */
    function acceptBidColony(uint colonyIndex) public onlyActiveAccount {
        // колония принадлежит продающему
        if (!ckColony.isOwnerColony(msg.sender, colonyIndex)) return;
        BidColony memory existBid = colonyBids[colonyIndex];
        if (existBid.price == 0) return;
        // передаем колонию новому владельцу
        if (!ckColony.transferColony(msg.sender, existBid.customer, colonyIndex)) return;
        colonyBids[colonyIndex] = BidColony(colonyIndex, 0x0, 0);
        // переведём бабки пользователю
        ckAccount.addPendingWithdrawals.value(existBid.price)(msg.sender);
        colonyOffers[colonyIndex] = OfferColony(colonyIndex, msg.sender, 0, 0x0, false);
        emit ColonyBidAccepted(colonyIndex, existBid.price, existBid.customer);
    }
    /*
        Снятие предложения на покупку колонии
    */
    function withdrawBidColony(uint colonyIndex) public onlyActiveAccount {
        BidColony memory existBidColony = colonyBids[colonyIndex];
        // существует предложение
        if (existBidColony.customer != msg.sender) return;
        // Обнуляем предложения по колонии
        emit ColonyBidWithdrawn(colonyIndex, existBidColony.price, msg.sender);
        // вернем деньги за предложение
        msg.sender.transfer(existBidColony.price);
        colonyBids[colonyIndex] = BidColony(colonyIndex, 0x0, 0);
    }
    /*
        Добавление предложения на продажу колонии
    */
    function offerColony(uint colonyIndex, uint price) public onlyActiveAccount {
        // колония принадлежит вызвавшему
        if (ckColony.isOwnerColony(msg.sender, colonyIndex)) return;
        if (price == 0) return;
        colonyOffers[colonyIndex] = OfferColony(colonyIndex, msg.sender, price, 0x0, true);
        emit ColonyOffered(colonyIndex, price, 0x0);
    }
    /*
        Добавление предложения на продажу колонии для конкретного покупателя
    */
    function offerColony(uint colonyIndex, uint price, address toAddress) public onlyActiveAccount {
        // колония принадлежит вызвавшему
        if (ckColony.isOwnerColony(msg.sender, colonyIndex)) return;
        if (price == 0) return;
        if (toAddress == 0x0) return;
        colonyOffers[colonyIndex] = OfferColony(colonyIndex, msg.sender, price, toAddress, true);
        emit ColonyOffered(colonyIndex, price, 0x0);
    }
    /*
        Покупка колонии
    */
    function buyColony(uint colonyIndex) public onlyActiveAccount payable {
        // ячейка не принадлежит текущему юзеру
        if (ckColony.isOwnerColony(msg.sender, colonyIndex)) return;
        OfferColony memory existOffer = colonyOffers[colonyIndex];
        if (!existOffer.isForSale) return;
        // если прислал мало денег
        if (msg.value < existOffer.price) return;
        // если указан конкретный покупатель
        if (existOffer.specialCustomer != 0x0 && existOffer.specialCustomer != msg.sender) return;
        // передаем ячейку новому владельцу
        if (!ckColony.transferColony(existOffer.owner, msg.sender, colonyIndex)) return;
        colonyOffers[colonyIndex] = OfferColony(colonyIndex, msg.sender, 0, 0x0, false);
        // переведём бабки пользователю
        ckAccount.addPendingWithdrawals.value(existOffer.price)(msg.sender);
        emit ColonyOfferBuy(colonyIndex, existOffer.price, msg.sender);
        // проверим не предлагал свои условия покупатель
        BidColony memory existBidColony = colonyBids[colonyIndex];
        // существует предложение
        if (existBidColony.customer == msg.sender) {
            // Обнуляем предложения по колонии
            emit ColonyBidWithdrawn(colonyIndex, existBidColony.price, msg.sender);
            // вернем деньги за предложение
            msg.sender.transfer(existBidColony.price);
            colonyBids[colonyIndex] = BidColony(colonyIndex, 0x0, 0);
        }
    }
    /*
        Снятие предложения на продажу колонии
    */
    function withdrawOfferColony(uint colonyIndex) public onlyActiveAccount {
        // колония принадлежит
        if (!ckColony.isOwnerColony(msg.sender, colonyIndex)) return;
        colonyOffers[colonyIndex] = OfferColony(colonyIndex, msg.sender, 0, 0x0, false);
        emit WithdrawOfferForColony(colonyIndex);
    }
    /*
        Колония выставлена на продажу
    */
    function isExistOfferColony(uint colonyIndex) public view returns (bool) {
        return colonyOffers[colonyIndex].isForSale;
    }
    /*
        Получить информацию предложения на продажу колонии
    */
    function getOfferColony(uint colonyIndex) public view returns (uint, address, uint, address, bool) {
        return (
        colonyOffers[colonyIndex].index,
        colonyOffers[colonyIndex].owner,
        colonyOffers[colonyIndex].price,
        colonyOffers[colonyIndex].specialCustomer,
        colonyOffers[colonyIndex].isForSale
        );
    }
    /*
        Получить информацию предложения на покупку колонии
    */
    function getBidColony(uint colonyIndex) public view returns (uint, address, uint) {
        return (
        colonyBids[colonyIndex].index,
        colonyBids[colonyIndex].customer,
        colonyBids[colonyIndex].price
        );
    }
}

contract CandyKillerAccount {
    function isCreateAndActive(address userAddress) public view returns (bool);

    function addPendingWithdrawals(address userAddress) public payable returns (bool);
}

contract CandyKillerColony {
    function isExistAndForSaleCell(address customer, uint cellIndex) public view returns (bool);

    function isOwnerEarthCell(address possibleOwner, uint cellIndex) public view returns (bool);

    function transferEarthCell(address from, address to, uint cellIndex) public returns (bool);

    function isExistColony(address customer, uint colonyIndex) public view returns (bool);

    function isOwnerColony(address possibleOwner, uint colonyIndex) public view returns (bool);

    function transferColony(address from, address to, uint colonyIndex) public returns (bool);
}