package com.mifos.apache.fineract.ui.online.customers.customerdetails;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.mifos.apache.fineract.R;
import com.mifos.apache.fineract.ui.base.MifosBaseActivity;
import com.mifos.apache.fineract.utils.ConstantKeys;

/**
 * @author Rajan Maurya
 *         On 26/06/17.
 */
public class CustomerDetailsActivity extends MifosBaseActivity {

    public static final String LOG_TAG = CustomerDetailsActivity.class.getSimpleName();

    private CustomerDetailsFragment customerDetailsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global_container);

        String identifier = getIntent().getExtras().getString(ConstantKeys.CUSTOMER_IDENTIFIER);

        customerDetailsFragment = CustomerDetailsFragment.newInstance(identifier);
        replaceFragment(customerDetailsFragment, false,
                R.id.global_container);

        showBackButton();
    }

    @Override
    public void onBackPressed() {
        try {
            Intent intent = new Intent();
            intent.putExtra(ConstantKeys.CUSTOMER_STATUS,
                    customerDetailsFragment.getCustomerStatus());
            setResult(RESULT_OK, intent);
            finish();
        } catch (NullPointerException e) {
            Log.d(LOG_TAG, e.getLocalizedMessage());
        }
        super.onBackPressed();
    }
}
