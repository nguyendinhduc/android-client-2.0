package com.mifos.apache.fineract.data.datamanager;

import com.mifos.apache.fineract.data.local.PreferencesHelper;
import com.mifos.apache.fineract.data.models.loan.LoanAccount;
import com.mifos.apache.fineract.data.models.loan.LoanAccountPage;
import com.mifos.apache.fineract.data.models.product.ProductPage;
import com.mifos.apache.fineract.data.remote.BaseApiManager;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Observable;

/**
 * @author Rajan Maurya
 *         On 07/07/17.
 */
@Singleton
public class DataManagerLoans extends MifosBaseDataManager {

    private final BaseApiManager baseApiManager;
    private final PreferencesHelper preferencesHelper;

    @Inject
    public DataManagerLoans(BaseApiManager baseApiManager, PreferencesHelper preferencesHelper,
            DataManagerAuth dataManagerAuth) {
        super(dataManagerAuth, preferencesHelper);
        this.baseApiManager = baseApiManager;
        this.preferencesHelper = preferencesHelper;
    }

    public Observable<LoanAccountPage> fetchCustomerLoanAccounts(
            String customeridentifier, Integer pageIndex, Integer size) {
        return authenticatedObservableApi(baseApiManager.getLoanApi()
                .fetchCustomerLoanAccounts(customeridentifier, pageIndex, size));
    }

    public Observable<LoanAccount> fetchCustomerLoanDetails(
            String productIdentifier, String caseIdentifier) {
        return authenticatedObservableApi(baseApiManager.getLoanApi()
                .fetchCustomerLoanDetails(productIdentifier, caseIdentifier));
    }

    public Observable<ProductPage> getProducts(Integer pageIndex, Integer size) {
        return authenticatedObservableApi(baseApiManager.getLoanApi()
                .getProducts(pageIndex, size, false));
    }

    public Completable createLoan(String productIdentifier, LoanAccount loanAccount) {
        return authenticatedCompletableApi(baseApiManager.getLoanApi()
                .createLoan(productIdentifier, loanAccount));
    }
}
