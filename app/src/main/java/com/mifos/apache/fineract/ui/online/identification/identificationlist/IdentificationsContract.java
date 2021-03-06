package com.mifos.apache.fineract.ui.online.identification.identificationlist;

import com.mifos.apache.fineract.data.models.customer.identification.Identification;
import com.mifos.apache.fineract.ui.base.MvpView;

import java.util.List;

/**
 * @author Rajan Maurya
 *         On 31/07/17.
 */
public interface IdentificationsContract {

    interface View extends MvpView {

        void showUserInterface();

        void showIdentification(List<Identification> identifications);

        void showProgressbar();

        void hideProgressbar();

        void showRecyclerView(boolean status);

        void showMessage(String message);
    }

    interface Presenter {

        void fetchIdentifications(String customerIdentifier);
    }
}
