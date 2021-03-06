package com.mifos.apache.fineract.ui.online.depositaccounts.createdepositaccount
        .createdepositactivity;

import android.content.Context;

import com.mifos.apache.fineract.R;
import com.mifos.apache.fineract.data.datamanager.DataManagerDeposit;
import com.mifos.apache.fineract.data.models.deposit.DepositAccount;
import com.mifos.apache.fineract.injection.ApplicationContext;
import com.mifos.apache.fineract.injection.ConfigPersistent;
import com.mifos.apache.fineract.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Rajan Maurya
 *         On 15/08/17.
 */
@ConfigPersistent
public class CreateDepositPresenter extends BasePresenter<CreateDepositContract.View>
        implements CreateDepositContract.Presenter {

    private DataManagerDeposit dataManagerDeposit;
    private CompositeDisposable compositeDisposable;

    @Inject
    public CreateDepositPresenter(@ApplicationContext Context context,
            DataManagerDeposit dataManagerDeposit) {
        super(context);
        this.dataManagerDeposit = dataManagerDeposit;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void attachView(CreateDepositContract.View mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        compositeDisposable.clear();
    }

    @Override
    public void createDepositAccount(DepositAccount depositAccount) {
        checkViewAttached();
        getMvpView().showProgressbar();
        compositeDisposable.add(dataManagerDeposit.createDepositAccount(depositAccount)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        getMvpView().hideProgressbar();
                        getMvpView().depositCreatedSuccessfully();
                    }

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().hideProgressbar();
                        getMvpView().showError(
                                context.getString(R.string.error_creating_deposit_account));
                    }
                })
        );
    }

    @Override
    public void updateDepositAccount(String accountIdentifier, DepositAccount depositAccount) {
        checkViewAttached();
        getMvpView().showProgressbar();
        compositeDisposable.add(
                dataManagerDeposit.updateDepositAccount(accountIdentifier, depositAccount)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableCompletableObserver() {
                            @Override
                            public void onComplete() {
                                getMvpView().hideProgressbar();
                                getMvpView().depositUpdatedSuccessfully();
                            }

                            @Override
                            public void onError(Throwable e) {
                                getMvpView().hideProgressbar();
                                getMvpView().showError(
                                        context.getString(R.string.error_updating_deposit_account));
                            }
                        })
        );
    }
}
