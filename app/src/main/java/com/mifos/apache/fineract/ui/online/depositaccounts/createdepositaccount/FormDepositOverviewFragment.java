package com.mifos.apache.fineract.ui.online.depositaccounts.createdepositaccount;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mifos.apache.fineract.R;
import com.mifos.apache.fineract.data.models.deposit.DepositAccount;
import com.mifos.apache.fineract.ui.adapters.BeneficiaryAdapter;
import com.mifos.apache.fineract.ui.base.MifosBaseActivity;
import com.mifos.apache.fineract.ui.base.MifosBaseFragment;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Rajan Maurya
 *         On 13/08/17.
 */
public class FormDepositOverviewFragment extends MifosBaseFragment implements Step,
        DepositOverViewContract {

    @BindView(R.id.tv_product)
    TextView tvProduct;

    @BindView(R.id.rv_beneficiary)
    RecyclerView rvBeneficiary;

    @BindView(R.id.tv_no_beneficiary)
    TextView tvNoBeneficiary;

    @BindView(R.id.ll_product_overview)
    LinearLayout llProductOverView;

    @Inject
    BeneficiaryAdapter beneficiaryAdapter;

    View rootView;

    public static FormDepositOverviewFragment newInstance() {
        FormDepositOverviewFragment fragment = new FormDepositOverviewFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MifosBaseActivity) getActivity()).getActivityComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_form_deposit_overview, container, false);
        ButterKnife.bind(this, rootView);

        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rvBeneficiary.setLayoutManager(staggeredGridLayoutManager);
        rvBeneficiary.setAdapter(beneficiaryAdapter);
        beneficiaryAdapter.hideDeleteImageStatus(true);

        return rootView;
    }

    @Override
    public void setProductInstance(DepositAccount depositAccount, String productName,
            DepositAction depositAction) {

        if (depositAction == DepositAction.CREATE) {
            tvProduct.setText(productName);
            llProductOverView.setVisibility(View.VISIBLE);
        }

        beneficiaryAdapter.setAllBeneficiary(depositAccount.getBeneficiaries());

        if (depositAccount.getBeneficiaries().size() == 0) {
            tvNoBeneficiary.setVisibility(View.VISIBLE);
            rvBeneficiary.setVisibility(View.GONE);
        } else {
            tvNoBeneficiary.setVisibility(View.GONE);
            rvBeneficiary.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public VerificationError verifyStep() {
        return null;
    }

    @Override
    public void onSelected() {
    }

    @Override
    public void onError(@NonNull VerificationError error) {
    }
}
