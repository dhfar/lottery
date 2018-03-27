package org.dhfar;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple7;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import rx.Observable;
import rx.functions.Func1;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 3.3.1.
 */
public class CandyKillerAccount extends Contract {
    private static final String BINARY = "606060405260018055612710600255341561001957600080fd5b60008054600160a060020a033316600160a060020a0319909116179055610e9b806100456000396000f3006060604052600436106101115763ffffffff7c010000000000000000000000000000000000000000000000000000000060003504166313f58fd1811461011657806319289287146101375780631b9e16ad146101dc578063220565de1461029f57806331c3f313146102b257806349d64c5f146102dc578063544e5b96146102f557806360e505c5146103145780636896fabf1461032a5780637ddbb5b71461033d578063892d0f6e146103505780638da5cb5b1461036f578063aebc95591461039e578063b0e8319b146103a6578063b9c8c449146103b9578063bfe4b20b146103cd578063df32754b146103e6578063e040e9a0146103f9578063f2fde38b1461040c578063f3f437031461042b575b600080fd5b341561012157600080fd5b610135600160a060020a036004351661044a565b005b341561014257600080fd5b6101ca60046024813581810190830135806020601f8201819004810201604051908101604052818152929190602084018383808284378201915050505050509190803590602001908201803590602001908080601f0160208091040260200160405190810160405281815292919060208401838380828437509496506104a695505050505050565b60405190815260200160405180910390f35b34156101e757600080fd5b6101ef61069f565b6040518781528515156040820152606081018590528315156080820152600160a060020a03831660a082015260c0810182905260e06020820181815290820188818151815260200191508051906020019080838360005b8381101561025e578082015183820152602001610246565b50505050905090810190601f16801561028b5780820380516001836020036101000a031916815260200191505b509850505050505050505060405180910390f35b34156102aa57600080fd5b6101ca6106e9565b34156102bd57600080fd5b6102c86004356106ef565b604051901515815260200160405180910390f35b34156102e757600080fd5b6102c8600435602435610729565b341561030057600080fd5b6101ca600160a060020a0360043516610849565b341561031f57600080fd5b6101ef600435610864565b341561033557600080fd5b6101ca610a28565b341561034857600080fd5b6101ca610a48565b341561035b57600080fd5b6102c8600160a060020a0360043516610a4e565b341561037a57600080fd5b610382610a97565b604051600160a060020a03909116815260200160405180910390f35b6102c8610aa6565b34156103b157600080fd5b6101ca610b6e565b6102c8600160a060020a0360043516610b7e565b34156103d857600080fd5b6102c8600435602435610be8565b34156103f157600080fd5b610135610cce565b341561040457600080fd5b6102c8610cf8565b341561041757600080fd5b610135600160a060020a0360043516610d29565b341561043657600080fd5b6101ca600160a060020a0360043516610d73565b60005433600160a060020a0390811691161461046557600080fd5b600160a060020a038116151561047a576104a3565b6006805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a0383161790555b50565b60006104b0610d85565b600160a060020a03331660009081526003602052604090206005015460ff16156104dd5760009150610698565b6001548152602081018490526002836040518082805190602001908083835b6020831061051b5780518252601f1990920191602091820191016104fc565b6001836020036101000a0380198251168184511680821785525050505050509050019150506020604051808303816000865af1151561055957600080fd5b5050604051805160408084019190915260016060840181905260006080850181905260a0850191909152600160a060020a0333168152600360205220829150815181556020820151816001019080516105b6929160200190610dc9565b5060408201516002820155606082015160038201805460ff19169115159190911790556080820151816004015560a082015160058201805460ff191691151591909117905560c082015160069190910155506001805460009081526004602052604090819020805473ffffffffffffffffffffffffffffffffffffffff191633600160a060020a0381169190911790915591547f85841522199c696c3d4a549fea06732153559ded5db5cf6dfa3bb099827f2c84929151600160a060020a03909216825260208201526040908101905180910390a16001805480820190915591505b5092915050565b60006106a9610e43565b600160a060020a03331660009081526003602052604081205481908190819081906106d390610864565b959d949c50929a50909850965094509092509050565b60015481565b6000805433600160a060020a0390811691161461070b57600080fd5b6000821161071b57506000610724565b50600281905560015b919050565b6000805433600160a060020a0390811691161461074557600080fd5b600082815260046020908152604080832054600160a060020a03168352600390915290206005015460ff16151561077e57506000610843565b600082815260046020818152604080842054600160a060020a03168452600390915290912001548390106107de57600082815260046020818152604080842054600160a060020a03168452600390915290912001805484900390556107e6565b506000610843565b7f5ccb25e760b69813d714bbbfa3ef14f68a90e2e90047e39cd05e8107a2b70d223384846040518084600160a060020a0316600160a060020a03168152602001838152602001828152602001935050505060405180910390a15060015b92915050565b600160a060020a031660009081526003602052604090205490565b600061086e610e43565b600080600080600061087e610d85565b60005433600160a060020a03908116911614806108b5575060008981526004602052604090205433600160a060020a039081169116145b15610a1c57600089815260046020908152604080832054600160a060020a031683526003909152908190209060e09051908101604052908160008201548152602001600182018054600181600116156101000203166002900480601f01602080910402602001604051908101604052809291908181526020018280546001816001161561010002031660029004801561098f5780601f106109645761010080835404028352916020019161098f565b820191906000526020600020905b81548152906001019060200180831161097257829003601f168201915b505050918352505060028201546020820152600382015460ff90811615156040830152600483015460608301526005830154161515608082015260069091015460a090910152905080518160200151826060015183608001518460a0015160008e815260046020526040902054600160a060020a031660c087015185955097509750975097509750975097505b50919395979092949650565b600160a060020a0333166000908152600360205260409020600401545b90565b60025481565b600160a060020a03811660009081526003602052604081206005015460ff168015610843575050600160a060020a03166000908152600360208190526040909120015460ff1690565b600054600160a060020a031681565b600160a060020a033316600090815260036020526040812060050154819060ff161515610ad65760009150610b6a565b60003411610ae75760009150610b6a565b60025434811515610af457fe5b33600160a060020a038116600090815260036020526040908190206004018054949093049384019092559192507f641df157745b52d6599a00a7e22851dbd6e9d1b2defbb2ba611e493a8565c0ab9190839051600160a060020a03909216825260208201526040908101905180910390a1600191505b5090565b6000610b7933610849565b905090565b60065460009033600160a060020a03908116911614610b9f57506000610724565b341515610bae57506000610724565b610bb782610a4e565b1515610bc557506000610724565b50600160a060020a03166000908152600560205260409020805434019055600190565b6000805433600160a060020a03908116911614610c0457600080fd5b600082815260046020908152604080832054600160a060020a03168352600390915290206005015460ff161515610c3d57506000610843565b600082815260046020818152604080842054600160a060020a03168452600390915291829020018054850190557feca2c485d4cfe01cd0328975f29768cb4f365cd63ceb7daf14cc9e64fa696d2e90339085908590518084600160a060020a0316600160a060020a03168152602001838152602001828152602001935050505060405180910390a150600192915050565b6000805473ffffffffffffffffffffffffffffffffffffffff191633600160a060020a0316179055565b600160a060020a033316600090815260036020819052604090912001805460ff19811660ff90911615179055600190565b60005433600160a060020a03908116911614610d4457600080fd5b6000805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a0392909216919091179055565b60056020526000908152604090205481565b60e06040519081016040528060008152602001610da0610e43565b815260006020820181905260408201819052606082018190526080820181905260a09091015290565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10610e0a57805160ff1916838001178555610e37565b82800160010185558215610e37579182015b82811115610e37578251825591602001919060010190610e1c565b50610b6a929150610e55565b60206040519081016040526000815290565b610a4591905b80821115610b6a5760008155600101610e5b5600a165627a7a72305820c0ae9067b06cd6c65255b2f389d7d433d057aca2563a24623dd1d0b3a8354e210029";

    protected CandyKillerAccount(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected CandyKillerAccount(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public List<CreateAccountEventResponse> getCreateAccountEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("CreateAccount", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(event, transactionReceipt);
        ArrayList<CreateAccountEventResponse> responses = new ArrayList<CreateAccountEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            CreateAccountEventResponse typedResponse = new CreateAccountEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.creator = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.account = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<CreateAccountEventResponse> createAccountEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("CreateAccount", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, CreateAccountEventResponse>() {
            @Override
            public CreateAccountEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(event, log);
                CreateAccountEventResponse typedResponse = new CreateAccountEventResponse();
                typedResponse.log = log;
                typedResponse.creator = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.account = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public List<BuyPixelWarsCoinsEventResponse> getBuyPixelWarsCoinsEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("BuyPixelWarsCoins", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(event, transactionReceipt);
        ArrayList<BuyPixelWarsCoinsEventResponse> responses = new ArrayList<BuyPixelWarsCoinsEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            BuyPixelWarsCoinsEventResponse typedResponse = new BuyPixelWarsCoinsEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.buyer = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.coins = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<BuyPixelWarsCoinsEventResponse> buyPixelWarsCoinsEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("BuyPixelWarsCoins", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, BuyPixelWarsCoinsEventResponse>() {
            @Override
            public BuyPixelWarsCoinsEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(event, log);
                BuyPixelWarsCoinsEventResponse typedResponse = new BuyPixelWarsCoinsEventResponse();
                typedResponse.log = log;
                typedResponse.buyer = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.coins = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public List<AccrualPixelWarsCoinsEventResponse> getAccrualPixelWarsCoinsEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("AccrualPixelWarsCoins", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(event, transactionReceipt);
        ArrayList<AccrualPixelWarsCoinsEventResponse> responses = new ArrayList<AccrualPixelWarsCoinsEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            AccrualPixelWarsCoinsEventResponse typedResponse = new AccrualPixelWarsCoinsEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.executor = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.pixelCount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.accountIndex = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<AccrualPixelWarsCoinsEventResponse> accrualPixelWarsCoinsEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("AccrualPixelWarsCoins", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, AccrualPixelWarsCoinsEventResponse>() {
            @Override
            public AccrualPixelWarsCoinsEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(event, log);
                AccrualPixelWarsCoinsEventResponse typedResponse = new AccrualPixelWarsCoinsEventResponse();
                typedResponse.log = log;
                typedResponse.executor = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.pixelCount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.accountIndex = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public List<WithdrawalPixelWarsCoinsEventResponse> getWithdrawalPixelWarsCoinsEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("WithdrawalPixelWarsCoins", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(event, transactionReceipt);
        ArrayList<WithdrawalPixelWarsCoinsEventResponse> responses = new ArrayList<WithdrawalPixelWarsCoinsEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            WithdrawalPixelWarsCoinsEventResponse typedResponse = new WithdrawalPixelWarsCoinsEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.executor = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.pixelCount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.accountIndex = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<WithdrawalPixelWarsCoinsEventResponse> withdrawalPixelWarsCoinsEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("WithdrawalPixelWarsCoins", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, WithdrawalPixelWarsCoinsEventResponse>() {
            @Override
            public WithdrawalPixelWarsCoinsEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(event, log);
                WithdrawalPixelWarsCoinsEventResponse typedResponse = new WithdrawalPixelWarsCoinsEventResponse();
                typedResponse.log = log;
                typedResponse.executor = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.pixelCount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.accountIndex = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public RemoteCall<TransactionReceipt> setColonyMarketPlace(String colonyMarketPlaceAddress) {
        final Function function = new Function(
                "setColonyMarketPlace", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(colonyMarketPlaceAddress)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> createAccount(String userEmail, String userPassword) {
        final Function function = new Function(
                "createAccount", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(userEmail), 
                new org.web3j.abi.datatypes.Utf8String(userPassword)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Tuple7<BigInteger, String, Boolean, BigInteger, Boolean, String, BigInteger>> getAccountInfo() {
        final Function function = new Function("getAccountInfo", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Bool>() {}, new TypeReference<Uint256>() {}, new TypeReference<Bool>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
        return new RemoteCall<Tuple7<BigInteger, String, Boolean, BigInteger, Boolean, String, BigInteger>>(
                new Callable<Tuple7<BigInteger, String, Boolean, BigInteger, Boolean, String, BigInteger>>() {
                    @Override
                    public Tuple7<BigInteger, String, Boolean, BigInteger, Boolean, String, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple7<BigInteger, String, Boolean, BigInteger, Boolean, String, BigInteger>(
                                (BigInteger) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (Boolean) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue(), 
                                (Boolean) results.get(4).getValue(), 
                                (String) results.get(5).getValue(), 
                                (BigInteger) results.get(6).getValue());
                    }
                });
    }

    public RemoteCall<BigInteger> nextAccountIndex() {
        final Function function = new Function("nextAccountIndex", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> setPixelWarsCoinPrice(BigInteger newPrice) {
        final Function function = new Function(
                "setPixelWarsCoinPrice", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(newPrice)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> withdrawalPixelWarsCoins(BigInteger pixelCount, BigInteger accountIndex) {
        final Function function = new Function(
                "withdrawalPixelWarsCoins", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(pixelCount), 
                new org.web3j.abi.datatypes.generated.Uint256(accountIndex)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> getAccountIndexByAddress(String ownerAccount) {
        final Function function = new Function("getAccountIndexByAddress", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(ownerAccount)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<Tuple7<BigInteger, String, Boolean, BigInteger, Boolean, String, BigInteger>> getAccountInfoByIndex(BigInteger indexAccount) {
        final Function function = new Function("getAccountInfoByIndex", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(indexAccount)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Bool>() {}, new TypeReference<Uint256>() {}, new TypeReference<Bool>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
        return new RemoteCall<Tuple7<BigInteger, String, Boolean, BigInteger, Boolean, String, BigInteger>>(
                new Callable<Tuple7<BigInteger, String, Boolean, BigInteger, Boolean, String, BigInteger>>() {
                    @Override
                    public Tuple7<BigInteger, String, Boolean, BigInteger, Boolean, String, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple7<BigInteger, String, Boolean, BigInteger, Boolean, String, BigInteger>(
                                (BigInteger) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (Boolean) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue(), 
                                (Boolean) results.get(4).getValue(), 
                                (String) results.get(5).getValue(), 
                                (BigInteger) results.get(6).getValue());
                    }
                });
    }

    public RemoteCall<BigInteger> getAccountBalance() {
        final Function function = new Function("getAccountBalance", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> pixelWarsCoinPrice() {
        final Function function = new Function("pixelWarsCoinPrice", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<Boolean> isCreateAndActive(String userAddress) {
        final Function function = new Function("isCreateAndActive", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(userAddress)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<String> owner() {
        final Function function = new Function("owner", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> buyPixelWarsCoins(BigInteger weiValue) {
        final Function function = new Function(
                "buyPixelWarsCoins", 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteCall<BigInteger> getAccountIndex() {
        final Function function = new Function("getAccountIndex", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> addPendingWithdrawals(String userAddress, BigInteger weiValue) {
        final Function function = new Function(
                "addPendingWithdrawals", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(userAddress)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteCall<TransactionReceipt> accrualPixelWarsCoins(BigInteger pixelCount, BigInteger accountIndex) {
        final Function function = new Function(
                "accrualPixelWarsCoins", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(pixelCount), 
                new org.web3j.abi.datatypes.generated.Uint256(accountIndex)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> owned() {
        final Function function = new Function(
                "owned", 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> deactivateAccount() {
        final Function function = new Function(
                "deactivateAccount", 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> transferOwnership(String newOwner) {
        final Function function = new Function(
                "transferOwnership", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(newOwner)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> pendingWithdrawals(String param0) {
        final Function function = new Function("pendingWithdrawals", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public static RemoteCall<CandyKillerAccount> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(CandyKillerAccount.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<CandyKillerAccount> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(CandyKillerAccount.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static CandyKillerAccount load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new CandyKillerAccount(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static CandyKillerAccount load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new CandyKillerAccount(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static class CreateAccountEventResponse {
        public Log log;

        public String creator;

        public BigInteger account;
    }

    public static class BuyPixelWarsCoinsEventResponse {
        public Log log;

        public String buyer;

        public BigInteger coins;
    }

    public static class AccrualPixelWarsCoinsEventResponse {
        public Log log;

        public String executor;

        public BigInteger pixelCount;

        public BigInteger accountIndex;
    }

    public static class WithdrawalPixelWarsCoinsEventResponse {
        public Log log;

        public String executor;

        public BigInteger pixelCount;

        public BigInteger accountIndex;
    }
}
