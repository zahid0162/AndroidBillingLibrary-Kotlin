package com.example.googlebilling

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.billingclient.api.*
import com.example.googlebilling.databinding.ActivityMainBinding
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.collect.ImmutableList

class MainActivity : AppCompatActivity() {
    lateinit var viewBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        val skuList = ArrayList<String>()
        skuList.add("test_prod_1")
        skuList.add("test_prod_2")
        skuList.add("test_prod_3")
        val purchasesUpdatedListener = PurchasesUpdatedListener { billingResult, purchses ->
        }

        var billingClient = BillingClient.newBuilder(this)
            .setListener(purchasesUpdatedListener)
            .enablePendingPurchases().build()

        viewBinding.buy.setOnClickListener {

            billingClient.startConnection(object : BillingClientStateListener {
                override fun onBillingServiceDisconnected() {

                }

                override fun onBillingSetupFinished(billingResult: BillingResult) {
//                    TODO("Not yet implemented")

                    if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {

                        val queryProductDetailsParams =
                            QueryProductDetailsParams.newBuilder()
                                .setProductList(
                                    ImmutableList.of(
                                        QueryProductDetailsParams.Product.newBuilder()
                                            .setProductId("product_id_example")
                                            .setProductType(BillingClient.ProductType.SUBS)
                                            .build()
                                    )
                                )
                                .build()
                        billingClient.queryProductDetailsAsync(queryProductDetailsParams) { billingResult,
                                                                                            productDetailsList ->
                            Toast.makeText(this@MainActivity,billingResult.toString(),Toast.LENGTH_SHORT).show()
                        }

                    }
                }

            })
        }
    }
}