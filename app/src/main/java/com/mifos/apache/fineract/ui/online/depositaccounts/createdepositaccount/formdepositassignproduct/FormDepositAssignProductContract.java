package com.mifos.apache.fineract.ui.online.depositaccounts.createdepositaccount.formdepositassignproduct;

import com.mifos.apache.fineract.data.models.deposit.DepositAccount;
import com.mifos.apache.fineract.data.models.deposit.ProductDefinition;
import com.mifos.apache.fineract.ui.base.MvpView;

import java.util.List;

/**
 * @author Rajan Maurya
 *         On 13/08/17.
 */
public interface FormDepositAssignProductContract {

    interface View extends MvpView {

        void showUserInterface();

        void editDepositAccountDetails(DepositAccount depositAccount);

        void showProductDefinitions(List<String> products);

        void showProgressbar();

        void hideProgressbar();

        void showError(String message);
    }

    interface Presenter {

        void fetchProductDefinitions();

        List<String> filterProductsName(List<ProductDefinition> productDefinitions);

        String getProductIdentifier(Integer position);
    }

}
