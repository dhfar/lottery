pragma solidity ^0.4.21;

import "./Owned.sol";

contract CandyKillerAccount is Owned {
    // идентификатор следующей учетной записи
    uint public nextAccountIndex = 1;
    // стоимость 1 единицы игровой валюты
    uint public pixelWarsCoinPrice = 10000;
    // список учетных записе
    mapping(address => Account) private accounts;
    mapping(uint => address) private indexOfAccounts;
    // Валюта пользователей
    mapping(address => uint) public pendingWithdrawals;
    // адреса магазинов
    address colonyMarketPlace;
    address candyKillerCharacter;
    /*
        Описание аккаунта
    */
    struct Account {
        uint index; // идентификатор
        string email; // почта
        bytes32 password; // хэшпароля
        bool isActivate; // активен/ неактивен
        uint pixelWarsCoin; // игровая валюта
        bool isCreated; //  создан
        uint freeExperienceCoin; //свободный опыт
    }
    /*
        События
    */
    // создание учетной записи
    event CreateAccount(address creator, uint account);
    // покупка игровой валюты
    event BuyPixelWarsCoins(address buyer, uint coins);
    // начисление игровой валюты
    event AccrualPixelWarsCoins(address executor, uint pixelCount, uint accountIndex);
    // списание игровой валюты
    event WithdrawalPixelWarsCoins(address executor, uint pixelCount, uint accountIndex);

    function CandyKillerAccount() public {
        owner = msg.sender;
    }
    /*
        Создание аккаунта
    */
    function createAccount(string userEmail, string userPassword) public returns (uint) {
        if (accounts[msg.sender].isCreated) return 0;
        Account memory newAccount;
        newAccount.index = nextAccountIndex;
        newAccount.email = userEmail;
        newAccount.password = sha256(userPassword);
        newAccount.isActivate = true;
        newAccount.pixelWarsCoin = 0;
        newAccount.isCreated = true;
        accounts[msg.sender] = newAccount;
        indexOfAccounts[nextAccountIndex] = msg.sender;
        emit CreateAccount(msg.sender, nextAccountIndex);
        nextAccountIndex++;
        return nextAccountIndex - 1;
    }
    /*
         Активация/деактивация аккаунта
    */
    function deactivateAccount() public returns (bool) {
        if (!accounts[msg.sender].isCreated) return false;
        accounts[msg.sender].isActivate = !accounts[msg.sender].isActivate;
        return true;
    }
    /*
        Получить информацию об учетной записи по идентификатору
        indexAccount - идентификатор учетной записи
        string - email, bool - активен/ неактивен, uint - игровая валюта, bool - создан, address - владелец, int - свободный опыт
    */
    function getAccountInfoByIndex(uint indexAccount) public view returns (uint, string, bool, uint, bool, address, uint) {
        if (msg.sender == owner || msg.sender == indexOfAccounts[indexAccount]) {
            Account memory userAccount = accounts[indexOfAccounts[indexAccount]];
            return (
            userAccount.index,
            userAccount.email,
            userAccount.isActivate,
            userAccount.pixelWarsCoin,
            userAccount.isCreated,
            indexOfAccounts[indexAccount],
            userAccount.freeExperienceCoin
            );
        }
    }
    /*
        Получить информацию об учетной записи по адресу вызвавшего функцию
        string - email, bool - активен/ неактивен, uint - игровая валюта, bool - создан, address - владелец, int - свободный опыт
    */
    function getAccountInfo() public view returns (uint, string, bool, uint, bool, address, uint) {
        return getAccountInfoByIndex(accounts[msg.sender].index);
    }
    /*
        Получить идентификатор учетной записи по адресу владельца
        uint - id учетной записи
    */
    function getAccountIndexByAddress(address ownerAccount) public view returns (uint){
        return accounts[ownerAccount].index;
    }
    /*
        Получить идентификатор учетной записи по адресу вызвавшего функцию
        uint - id учетной записи
    */
    function getAccountIndex() public view returns (uint){
        return getAccountIndexByAddress(msg.sender);
    }
    /*
        Получить баланс монет аккаунта
    */
    function getAccountBalance() public view returns (uint) {
        return accounts[msg.sender].pixelWarsCoin;
    }
    /*
        Задать цену покупки игровой валюты(в газе)
    */
    function setPixelWarsCoinPrice(uint newPrice) public onlyOwner returns (bool) {
        if (newPrice <= 0) return false;
        pixelWarsCoinPrice = newPrice;
        return true;
    }
    /*
        Покупка игровой валюты
    */
    function buyPixelWarsCoins() public payable returns (bool) {
        if (!accounts[msg.sender].isCreated) return false;
        if (msg.value <= 0) return false;
        uint coins = msg.value / pixelWarsCoinPrice;
        accounts[msg.sender].pixelWarsCoin += coins;
        emit BuyPixelWarsCoins(msg.sender, coins);
        return true;
    }
    /*
        Начисление игровой валюты на учетную запись
    */
    function accrualPixelWarsCoins(uint pixelCount, uint accountIndex) public onlyOwner returns (bool) {
        if (!accounts[indexOfAccounts[accountIndex]].isCreated) return false;
        accounts[indexOfAccounts[accountIndex]].pixelWarsCoin += pixelCount;
        emit AccrualPixelWarsCoins(msg.sender, pixelCount, accountIndex);
        return true;
    }
    /*
        Списание игровой валюты с учетной записи
    */
    function withdrawalPixelWarsCoins(uint pixelCount, uint accountIndex) public onlyOwner returns (bool) {
        if (!accounts[indexOfAccounts[accountIndex]].isCreated) return false;
        if (accounts[indexOfAccounts[accountIndex]].pixelWarsCoin >= pixelCount) {
            accounts[indexOfAccounts[accountIndex]].pixelWarsCoin -= pixelCount;
        } else {
            return false;
        }
        emit WithdrawalPixelWarsCoins(msg.sender, pixelCount, accountIndex);
        return true;
    }
    /*
        Учетная запись создана и не заблокирована
    */
    function isCreateAndActive(address userAddress) public view returns (bool) {
        return (accounts[userAddress].isCreated && accounts[userAddress].isActivate);
    }
    /*
        Задать адрес контракта магазина колонии
    */
    function setColonyMarketPlace(address colonyMarketPlaceAddress) public onlyOwner {
        if (colonyMarketPlaceAddress == 0x0) return;
        colonyMarketPlace = colonyMarketPlaceAddress;
    }
    /*
        Задать адрес контракта отряда
    */
    function setCandyKillerCharacter(address candyKillerCharacterAddress) public onlyOwner {
        if (candyKillerCharacterAddress == 0x0) return;
        candyKillerCharacter = candyKillerCharacterAddress;
    }
    /*
        Записать средства на счет пользователя
    */
    function addPendingWithdrawals(address userAddress) public payable returns (bool){
        if (msg.sender != colonyMarketPlace) return false;
        if (msg.value == 0) return false;
        if (!isCreateAndActive(userAddress)) return false;
        pendingWithdrawals[userAddress] += msg.value;
        return true;
    }
    /*
        Начисление свободного опыта
    */
    function accrueFreeExperienceCoin(address accountOwner, uint accrueCoins) public returns (bool) {
        if(msg.sender != candyKillerCharacter) return false;
        if(!isCreateAndActive(accountOwner)) return false;
        accounts[accountOwner].freeExperienceCoin += accrueCoins;
        return true;
    }
    /*
        Списание свободного опыта
    */
    function writeOffFreeExperienceCoin(address accountOwner, uint writeOffCoins) public returns (bool) {
        if(msg.sender != candyKillerCharacter) return false;
        if(!isCreateAndActive(accountOwner)) return false;
        if(writeOffCoins > accounts[accountOwner].freeExperienceCoin) return false;
        accounts[accountOwner].freeExperienceCoin -= writeOffCoins;
        return true;
    }
    /*
        Получить кол-во свободного опыта на учетной записе
    */
    function getFreeExperienceCoinCount(address accountOwner) public view returns (uint) {
        return accounts[accountOwner].freeExperienceCoin;
    }
}