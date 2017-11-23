pragma solidity ^0.4.16;
import "truffle/Assert.sol";
import "../contracts/lottery_6_45.sol";

contract TestUpdatePrizeCombination {

    /*
     *тестирование лотереи с верным статусом
     */
    function testUpdatePrizeCombination() public{
    lottery_6_45 lottery = new lottery_6_45();
    Assert.isTrue(lottery.buyTicket(1,2,3,4,5,6,0,0,0,0,0,0,0), "Билет 1,2,3,4,5,6,0,0,0,0,0,0,0 допустим");
    Assert.isTrue(lottery.buyTicket(1,2,3,10,20,40,0,0,0,0,0,0,0), "Билет 1,2,3,10,20,40,0,0,0,0,0,0,0 допустим");
    Assert.isFalse(lottery.buyTicket(1,2,3,4,5,67,0,0,0,0,0,0,0), "Билет 1,2,3,4,5,67,0,0,0,0,0,0,0 недопустим");
    Assert.isTrue(lottery.buyTicket(1,2,3,10,20,4,30,0,0,0,0,0,0), "Билет 1,2,3,10,20,4,30,0,0,0,0,0,0 допустим");
    Assert.equal(lottery.JackPot(),36, "Джек пот должен быть равен 36");
    Assert.equal(lottery.regularPrize(),36, "Основной приз должен быть равен 36");
    Assert.isFalse(lottery.updatePrizeCombination(1,2,3,4,5,6),"Лотерея еще не сменила статус на неактивный, и ей нельзя менять призовую комбинацию");
    lottery.changeStatus(false);
    Assert.isTrue(lottery.finishLottery(),'Лотерея активна и поэтому проведется');
    var (ticket_numbers, ticket_prize_level,numbers_in_ticket, money) = lottery.getTicket(0,0);
    Assert.equal(money,0,"никто ничего не берет, так как призовая комбинация нулевая");
    var (ticket_numbers1, ticket_prize_level1,numbers_in_ticket1, money1) = lottery.getTicket(0,1);
    Assert.equal(money1,0,"никто ничего не берет, так как призовая комбинация нулевая");
    var (ticket_numbers2, ticket_prize_level2,numbers_in_ticket2, money2) = lottery.getTicket(0,2);
    Assert.equal(money2,0,"никто ничего не берет, так как призовая комбинация нулевая");
    }
}