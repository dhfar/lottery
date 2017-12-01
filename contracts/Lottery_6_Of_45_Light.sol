pragma solidity ^0.4.16;
    
import "./DHFBaseCurrency.sol";
    
/*смарт-контракт лотереи 4 из 20
 *4 коина идет в джек-пот, 4 - на обычные призы, 2 остается и распределяется между держателями контракта
 *Создано Вопиловым А.
 *1.12.2017
 */
contract Lottery_6_Of_45_Light is DHFBaseCurrency 
{
    uint public JackPot = 0; //Размер Джек Пота
    uint public regularPrize = 0; //Размер основного приза лотереи
    uint public ticketPrice = 10; // цена билета
    uint public JackPotAssignment = 40; // процент отчислений в Джек-Пот от каждого билета
    uint public regularPrizeAssignment = 40; //процент отчислений в регулярный призовой фонд
    uint public prize_combination_size = 6;//количество чисел в призовой комбинации
    //структура билета
    struct ticket{
        address owner; //владелец билета
        string time; //время и дата покупки билета
        uint8[13] numbers; //числа в номере - сейчас сделаем ограниченное число - точно 4 числа в билете
        uint8 compliance_level; //число совпавших номеров с выигрышной комбинацией - инициализируется нулем
        uint8 valuable_numbers;//значащих чисел в билете - ненулевых
        uint money;//выграно данным билетом денег
    }
    //структура 1го тиража лотереи
    struct lottery{
        string date; // дата проведения лотереи
        mapping(uint => ticket) tickets; //все билеты одной лотереи.
        uint tickets_count; //количество билетов в этой лотерее
        uint[6] prize_combination; //выигрышная комбинация текущего тиража лотереи
        bool active; //ведется или не ведется прием ставок
        bool played; //проведен ли розыгрыш
    }
    mapping (uint => lottery) public lotteries;
    
    /*Инициализация лотереи
     *задаем первой лотерее дату и количество билетов в ней
     *Создано Вопиловым А.
     *3.11.2017
     */
    function Lottery_6_Of_45_Light() DHFBaseCurrency (10000, "Lottery 6 of 45", "L6x45") public
    { 
        lotteries[0].date = "06.11.2017";
        lotteries[0].tickets_count = 0;
        lotteries[0].prize_combination = [0,0,0,0,0,0];
        lotteries[0].active = true;
        lotteries[0].played = false;
    }
    
    /*Расчет факториала числа
     *Создано Вопиловым А.
     *@number uint256 число для расчета факториала
     *return uint256 fact - значение факториала числа
     *10.11.2017
     */
    function factorial(uint number) public constant returns(uint fact) 
    {
        fact = 1; 
        if(number == 0) return 1;
        for(uint i = 1;i<=number;i++)
            fact = fact * i;
        return fact;
    }
    
    /*Расчет цены билета от количества номеров в нем
     *Создано Вопиловым А.
     *@number_count uint8 выбрано чисел в билете
     *return uint256 price - результирующая цена билета - рассчитывается от базовой цены билета, помноженной на число комбинаций в нем
     *1.12.2017
     */
    function getTicketPrice(uint number_count) public constant returns (uint price)
    {
        if(number_count < prize_combination_size) return 0;
        return (factorial(number_count) * ticketPrice) / (factorial(number_count - prize_combination_size) * factorial(prize_combination_size));
    }
    
    
}