package com.mifos.apache.fineract.ui.online.depositaccounts.depositaccountdetails;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mifos.apache.fineract.R;
import com.mifos.apache.fineract.data.models.deposit.DepositAccount;
import com.mifos.apache.fineract.ui.base.MifosBaseActivity;
import com.mifos.apache.fineract.ui.base.MifosBaseFragment;
import com.mifos.apache.fineract.ui.base.Toaster;
import com.mifos.apache.fineract.ui.online.depositaccounts.createdepositaccount.DepositAction;
import com.mifos.apache.fineract.ui.online.depositaccounts.createdepositaccount
        .createdepositactivity.CreateDepositActivity;
import com.mifos.apache.fineract.utils.ConstantKeys;
import com.mifos.apache.fineract.utils.StatusUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Rajan Maurya
 *         On 12/07/17.
 */
public class DepositAccountDetailsFragment extends MifosBaseFragment
        implements DepositAccountDetailsContract.View {

    @BindView(R.id.cl_customer_deposit_details)
    CoordinatorLayout clCustomerDepositDetails;

    @BindView(R.id.tv_deposit_current_status)
    TextView tvDepositCurrentStatus;

    @BindView(R.id.iv_deposit_current_status)
    ImageView ivDepositCurrentStatus;

    @BindView(R.id.tv_account)
    TextView tvAccount;

    @BindView(R.id.tv_balance)
    TextView tvBalance;

    @BindView(R.id.tv_beneficiaries)
    TextView tvBeneficiaries;

    @BindView(R.id.rl_error)
    RelativeLayout rlError;

    @BindView(R.id.tv_error)
    TextView tvError;

    @BindView(R.id.fab_edit_deposit_account)
    FloatingActionButton fabEditDepositAccount;

    @Inject
    DepositAccountDetailsPresenter customerDepositDetailsPresenter;

    View rootView;

    private String accountIdentifier;
    private DepositAccount depositAccount;

    public static DepositAccountDetailsFragment newInstance(String accountIdentifier) {
        DepositAccountDetailsFragment fragment = new DepositAccountDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ConstantKeys.ACCOUNT_IDENTIFIER, accountIdentifier);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            accountIdentifier = getArguments().getString(ConstantKeys.ACCOUNT_IDENTIFIER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_customer_deposit_details, container, false);
        ((MifosBaseActivity) getActivity()).getActivityComponent().inject(this);
        ButterKnife.bind(this, rootView);
        customerDepositDetailsPresenter.attachView(this);
        setToolbarTitle(getString(R.string.deposit_account));

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        clCustomerDepositDetails.setVisibility(View.GONE);
        customerDepositDetailsPresenter.fetchDepositAccountDetails(accountIdentifier);
    }

    @OnClick(R.id.iv_retry)
    void onRetry() {
        clCustomerDepositDetails.setVisibility(View.GONE);
        rlError.setVisibility(View.GONE);
        customerDepositDetailsPresenter.fetchDepositAccountDetails(accountIdentifier);
    }

    @OnClick(R.id.fab_edit_deposit_account)
    void editDepositAccount() {
        Intent intent = new Intent(getActivity(), CreateDepositActivity.class);
        intent.putExtra(ConstantKeys.CUSTOMER_IDENTIFIER, depositAccount.getCustomerIdentifier());
        intent.putExtra(ConstantKeys.DEPOSIT_ACCOUNT, depositAccount);
        intent.putExtra(ConstantKeys.DEPOSIT_ACTION, DepositAction.EDIT);
        startActivity(intent);
    }

    @Override
    public void showDepositDetails(DepositAccount depositAccount) {
        this.depositAccount = depositAccount;
        clCustomerDepositDetails.setVisibility(View.VISIBLE);
        rlError.setVisibility(View.GONE);

        StatusUtils.setDepositAccountStatusIcon(depositAccount.getState(),
                ivDepositCurrentStatus, getActivity());
        tvDepositCurrentStatus.setText(depositAccount.getState().name());
        tvAccount.setText(depositAccount.getAccountIdentifier());
        tvBalance.setText(String.valueOf(depositAccount.getBalance()));

        if (depositAccount.getBeneficiaries().size() == 0) {
            tvBeneficiaries.setText(R.string.no_beneficiary);
        } else {
            tvBeneficiaries.setText(depositAccount.getBeneficiaries().toString());
        }

        if (depositAccount.getState() == DepositAccount.State.PENDING) {
            fabEditDepositAccount.setVisibility(View.VISIBLE);
        } else {
            fabEditDepositAccount.setVisibility(View.GONE);
        }
    }

    @Override
    public void showProgressbar() {
        showMifosProgressBar();
    }

    @Override
    public void hideProgressbar() {
        hideMifosProgressBar();
    }

    @Override
    public void showError(String message) {
        clCustomerDepositDetails.setVisibility(View.GONE);
        rlError.setVisibility(View.VISIBLE);
        tvError.setText(message);
        Toaster.show(rootView, message);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        hideMifosProgressBar();
        customerDepositDetailsPresenter.detachView();
    }
}
