package com.kooloco.uilocal.organaization.fragment;
/**
 * Created by hlink44 on 11/10/17.
 */

import android.app.DatePickerDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.github.siyamed.shapeimageview.mask.PorterShapeImageView;
import com.kooloco.R;
import com.kooloco.core.Common;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.exception.ApplicationException;
import com.kooloco.model.Bank;
import com.kooloco.model.CountryList;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.ui.filter.adapter.FilterAdapter;
import com.kooloco.uilocal.organaization.adapter.AddBankListAdapter;
import com.kooloco.uilocal.organaization.adapter.BankCountryCodeAdapter;
import com.kooloco.uilocal.organaization.presenter.AddBankPresenter;
import com.kooloco.uilocal.organaization.view.AddBankView;
import com.kooloco.util.Validator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class AddBankFragment extends BaseFragment<AddBankPresenter, AddBankView> implements AddBankView {


    @BindView(R.id.editTextFirstName)
    AppCompatEditText editTextFirstName;
    @BindView(R.id.editTextLastName)
    AppCompatEditText editTextLastName;
    @BindView(R.id.editTextBankName)
    AppCompatEditText editTextBankName;
    @BindView(R.id.editTextAddress)
    AppCompatEditText editTextAddress;
    @BindView(R.id.chooseCountry)
    AppCompatEditText chooseCountry;
    @BindView(R.id.recyclerAppLanguage)
    RecyclerView recyclerAppLanguage;
    @BindView(R.id.linearLayoutCountry)
    LinearLayout linearLayoutCountry;
    @BindView(R.id.editTextZipCode)
    AppCompatEditText editTextZipCode;
    @BindView(R.id.chooseDateOfBirth)
    AppCompatEditText chooseDateOfBirth;
    @BindView(R.id.frameLayoutIdentifiy)
    FrameLayout frameLayoutIdentifiy;
    @BindView(R.id.textViewNote)
    AppCompatTextView textViewNote;
    @BindView(R.id.customTextViewNote)
    AppCompatTextView customTextViewNote;
    @BindView(R.id.buttonSubmit)
    AppCompatButton buttonSubmit;
    @BindView(R.id.imageViewIdentitiy)
    PorterShapeImageView imageViewIdentitiy;
    @BindView(R.id.recycleraddBank)
    RecyclerView recycleraddBank;
    @BindView(R.id.customTextViewAddBank)
    AppCompatTextView customTextViewAddBank;
    @BindView(R.id.linearLayoutAddBank)
    LinearLayout linearLayoutAddBank;
    @BindView(R.id.linearLayoutPick)
    LinearLayout linearLayoutPick;
    @BindView(R.id.toolbarTitle)
    AppCompatTextView toolbarTitle;
    @BindView(R.id.imageViewBack)
    ImageView imageViewBack;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.editTextAdditionalInstructions)
    AppCompatEditText editTextAdditionalInstructions;


    private boolean isEdit = false;
    @Inject
    Validator validator;

    private Calendar birthDayCal;
    private String dateOfBirth = "";
    private String selectImagePath = "";
    List<Bank> banks;
    private String countryId = "";
    private CountryList countrySelect;


    @BindView(R.id.editTextBicSwift1)
    AppCompatEditText editTextBicSwift1;
    @BindView(R.id.editTextNumber1)
    AppCompatEditText editTextNumber1;
    @BindView(R.id.editTextBsbBankId2)
    AppCompatEditText editTextBsbBankId2;
    @BindView(R.id.editTextBsbBranchId2)
    AppCompatEditText editTextBsbBranchId2;
    @BindView(R.id.editTextNumber2)
    AppCompatEditText editTextNumber2;
    @BindView(R.id.linearLayout1)
    LinearLayout linearLayout1;
    @BindView(R.id.linearLayout2)
    LinearLayout linearLayout2;
    @BindView(R.id.editTextBankCode3)
    AppCompatEditText editTextBankCode3;
    @BindView(R.id.editTextBranchCode3)
    AppCompatEditText editTextBranchCode3;
    @BindView(R.id.editTextNumber3)
    AppCompatEditText editTextNumber3;
    @BindView(R.id.linearLayout3)
    LinearLayout linearLayout3;
    @BindView(R.id.editTextBankCode4)
    AppCompatEditText editTextBankCode4;
    @BindView(R.id.editTextBranchCode4)
    AppCompatEditText editTextBranchCode4;
    @BindView(R.id.editTextNumber4)
    AppCompatEditText editTextNumber4;
    @BindView(R.id.linearLayout4)
    LinearLayout linearLayout4;
    @BindView(R.id.editTextBranchName5)
    AppCompatEditText editTextBranchName5;
    @BindView(R.id.editTextCity5)
    AppCompatEditText editTextCity5;
    @BindView(R.id.editTextProvince5)
    AppCompatEditText editTextProvince5;
    @BindView(R.id.editTextNumber5)
    AppCompatEditText editTextNumber5;
    @BindView(R.id.linearLayout5)
    LinearLayout linearLayout5;
    @BindView(R.id.editTextIfscCode6)
    AppCompatEditText editTextIfscCode6;
    @BindView(R.id.editTextNumber6)
    AppCompatEditText editTextNumber6;
    @BindView(R.id.linearLayout6)
    LinearLayout linearLayout6;
    @BindView(R.id.editTextNumber7)
    AppCompatEditText editTextNumber7;
    @BindView(R.id.editTextBranchName7)
    AppCompatEditText editTextBranchName7;
    @BindView(R.id.linearLayout7)
    LinearLayout linearLayout7;
    @BindView(R.id.editTextBankCode8)
    AppCompatEditText editTextBankCode8;
    @BindView(R.id.editTextBranchCode8)
    AppCompatEditText editTextBranchCode8;
    @BindView(R.id.editTextNumber8)
    AppCompatEditText editTextNumber8;
    @BindView(R.id.linearLayout8)
    LinearLayout linearLayout8;
    @BindView(R.id.editTextBicSwift9)
    AppCompatEditText editTextBicSwift9;
    @BindView(R.id.editTextBankBranchCode9)
    AppCompatEditText editTextBankBranchCode9;
    @BindView(R.id.editTextNumber9)
    AppCompatEditText editTextNumber9;
    @BindView(R.id.linearLayout9)
    LinearLayout linearLayout9;
    @BindView(R.id.editTextClabe10)
    AppCompatEditText editTextClabe10;
    @BindView(R.id.linearLayout10)
    LinearLayout linearLayout10;
    @BindView(R.id.editTextAccountNumber11)
    AppCompatEditText editTextAccountNumber11;
    @BindView(R.id.linearLayout11)
    LinearLayout linearLayout11;
    @BindView(R.id.editTextBankCode12)
    AppCompatEditText editTextBankCode12;
    @BindView(R.id.editTextNumber12)
    AppCompatEditText editTextNumber12;
    @BindView(R.id.linearLayout12)
    LinearLayout linearLayout12;
    @BindView(R.id.editTextBicSwift13)
    AppCompatEditText editTextBicSwift13;
    @BindView(R.id.editTextBankCode13)
    AppCompatEditText editTextBankCode13;
    @BindView(R.id.editTextNumber13)
    AppCompatEditText editTextNumber13;
    @BindView(R.id.linearLayout13)
    LinearLayout linearLayout13;
    @BindView(R.id.editTextBicSwift14)
    AppCompatEditText editTextBicSwift14;
    @BindView(R.id.editTextRoutingNumber14)
    AppCompatEditText editTextRoutingNumber14;
    @BindView(R.id.editTextNumber14)
    AppCompatEditText editTextNumber14;
    @BindView(R.id.linearLayout14)
    LinearLayout linearLayout14;
    @BindView(R.id.editTextBranchSortingCode15)
    AppCompatEditText editTextBranchSortingCode15;
    @BindView(R.id.editTextNumber15)
    AppCompatEditText editTextNumber15;
    @BindView(R.id.editTextBuildingSocietyAccount15)
    AppCompatEditText editTextBuildingSocietyAccount15;
    @BindView(R.id.linearLayout15)
    LinearLayout linearLayout15;
    @BindView(R.id.editTextNumber16)
    AppCompatEditText editTextNumber16;
    @BindView(R.id.editTextRoutingNumber16)
    AppCompatEditText editTextRoutingNumber16;
    @BindView(R.id.linearLayout16)
    LinearLayout linearLayout16;
    @BindView(R.id.editTextBicSwift17)
    AppCompatEditText editTextBicSwift17;
    @BindView(R.id.editTextNumber17)
    AppCompatEditText editTextNumber17;
    @BindView(R.id.linearLayout17)
    LinearLayout linearLayout17;

    @BindView(R.id.chooseAccountType)
    AppCompatEditText chooseAccountType;
    @BindView(R.id.recyclerAccountType)
    RecyclerView recyclerAccountType;
    @BindView(R.id.linearLayoutAccountType)
    LinearLayout linearLayoutAccountType;
    @BindView(R.id.linearLayoutAccountTypeMain)
    LinearLayout linearLayoutAccountTypeMain;

    @BindView(R.id.chooseAccountType12)
    AppCompatEditText chooseAccountType12;
    @BindView(R.id.recyclerAccountType12)
    RecyclerView recyclerAccountType12;
    @BindView(R.id.linearLayoutAccountType12)
    LinearLayout linearLayoutAccountType12;
    @BindView(R.id.editTextTaxId12)
    AppCompatEditText editTextTaxId12;

    private int selectPosition = 0;

    private String accountType = "", accountType12 = "";

    @Override
    protected int createLayout() {
        return R.layout.fragment_local_add_bank;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected AddBankView createView() {
        return this;
    }

    AddBankListAdapter addBankListAdapter;

    @Override
    protected void bindData() {

        // showPosition(selectPosition);

        InputFilter[] inputFilters = {
                new InputFilter() {
                    public CharSequence filter(CharSequence src, int start,
                                               int end, Spanned dst, int dstart, int dend) {
                        if (src.toString().matches("[a-zA-Z ]+")) {
                            return src;
                        }
                        return "";
                    }
                }
        };

        editTextFirstName.setFilters(inputFilters);

        editTextLastName.setFilters(inputFilters);

        chooseCountry.setSelected(false);

        banks = new ArrayList<>();

        presenter.getData();

        recycleraddBank.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        addBankListAdapter = new AddBankListAdapter(getActivity(), banks, new AddBankListAdapter.CallBack() {
            @Override
            public void onClickItem(int position, Bank bank) {
                presenter.callChooseSelect(bank);
            }

            @Override
            public void onClickDelete(int position, Bank bank) {
                presenter.callDelete(position, bank);
            }
        });

        recycleraddBank.setAdapter(addBankListAdapter);


        //Set Data account type Form 12


        recyclerAccountType12.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        List<String> strings12 = new ArrayList<>();

        strings12.add(getString(R.string.individual));
        strings12.add(getString(R.string.individual_enterprice));

        recyclerAccountType12.setAdapter(new FilterAdapter(getActivity(), strings12, position -> {
            accountType12 = strings12.get(position);

            chooseAccountType12.setText(accountType12);
            chooseAccountType12.setSelected(!chooseAccountType12.isSelected());
            linearLayoutAccountType12.setVisibility(chooseAccountType12.isSelected() ? View.VISIBLE : View.GONE);

        }));


        //Set Data saving and check bank account

        recyclerAccountType.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        List<String> strings = new ArrayList<>();

        strings.add(getString(R.string.checking));
        strings.add(getString(R.string.saving));

        recyclerAccountType.setAdapter(new FilterAdapter(getActivity(), strings, position -> {
            accountType = strings.get(position);

            chooseAccountType.setText(accountType);
            chooseAccountType.setSelected(!chooseAccountType.isSelected());
            linearLayoutAccountType.setVisibility(chooseAccountType.isSelected() ? View.VISIBLE : View.GONE);

        }));
    }

    @OnClick({R.id.imageViewBack, R.id.buttonSubmit, R.id.chooseCountry, R.id.chooseAccountType, R.id.chooseAccountType12})
    public void onViewClickedDone(View view) {
        switch (view.getId()) {
            case R.id.imageViewBack:
                goBack();
                break;
            case R.id.buttonSubmit:
                // getActivity().finish();

                try {


                    Map<String, String> map = new HashMap<>();

                    if (chooseCountry.getText().toString().isEmpty()) {
                        ApplicationException applicationException = new ApplicationException(getString(R.string.val_bank_select_country));
                        throw applicationException;
                    }

                    validator.submit(editTextFirstName).checkEmpty().errorMessage(R.string.val_bank_first_name).check();
                    validator.submit(editTextLastName).checkEmpty().errorMessage(R.string.val_bank_last_name).check();

                    validator.submit(editTextBankName).checkEmpty().errorMessage(R.string.val_enter_bank_name).check();

                    map = checkValidation(selectPosition);


                    if (linearLayoutAccountTypeMain.getVisibility() == View.VISIBLE) {
                        if (chooseAccountType.getText().toString().isEmpty()) {
                            ApplicationException applicationException = new ApplicationException(getString(R.string.val_add_bank_account_type));
                            throw applicationException;
                        }
                        map.put(Common.BankFields.ACCOUNT_TYPE, chooseAccountType.getText().toString());
                    }

                    validator.submit(editTextAddress).checkEmpty().errorMessage(R.string.val_bank_address).check();

                    validator.submit(editTextZipCode).checkEmpty().errorMessage(R.string.val_bank_zip_code).check();

                    if (chooseDateOfBirth.getText().toString().isEmpty()) {
                        ApplicationException applicationException = new ApplicationException(getString(R.string.val_bank_date_of_birth));
                        throw applicationException;
                    }

                    if (selectImagePath.isEmpty()) {
                        ApplicationException applicationException = new ApplicationException(getString(R.string.val_bank_id_document));
                        throw applicationException;
                    }


                    presenter.callWs(isEdit,
                            editTextFirstName.getText().toString().trim(),
                            editTextLastName.getText().toString().trim(),
                            editTextBankName.getText().toString().trim(),
                            editTextAddress.getText().toString().trim(),
                            editTextZipCode.getText().toString().trim(),
                            dateOfBirth,
                            selectImagePath,
                            countryId,
                            map, editTextAdditionalInstructions.getText().toString().trim());

                } catch (ApplicationException e) {
                    e.printStackTrace();
                    showMessage(e.getMessage());
                }
                break;
            case R.id.chooseCountry:
                chooseCountry.setSelected(!chooseCountry.isSelected());
                linearLayoutCountry.setVisibility(chooseCountry.isSelected() ? View.VISIBLE : View.GONE);
                break;
            case R.id.chooseAccountType:
                chooseAccountType.setSelected(!chooseAccountType.isSelected());
                linearLayoutAccountType.setVisibility(chooseAccountType.isSelected() ? View.VISIBLE : View.GONE);
                break;
            case R.id.chooseAccountType12:
                chooseAccountType12.setSelected(!chooseAccountType12.isSelected());
                linearLayoutAccountType12.setVisibility(chooseAccountType12.isSelected() ? View.VISIBLE : View.GONE);
                break;
        }
    }

    @Override
    public void setIsEdit(boolean isEdit) {
        this.isEdit = isEdit;
    }

    @Override
    public void setDataCountry(List<CountryList> data) {

        recyclerAppLanguage.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        recyclerAppLanguage.setAdapter(new BankCountryCodeAdapter(getActivity(), data, new BankCountryCodeAdapter.CallBack() {
            @Override
            public void onClick(int position) {
                chooseCountry.setText(data.get(position).getName());
                countryId = data.get(position).getId();
                countrySelect = data.get(position);

                selectPosition = 0;
                try {
                    selectPosition = Integer.parseInt(data.get(position).getFormNo());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

                showPosition(selectPosition);

                chooseCountry.setSelected(!chooseCountry.isSelected());
                linearLayoutCountry.setVisibility(chooseCountry.isSelected() ? View.VISIBLE : View.GONE);
            }
        }));
    }

    @Override
    public void setDataBank(List<Bank> dataBank) {
        banks.clear();
        banks.addAll(dataBank);

        if (addBankListAdapter != null) {
            addBankListAdapter.setSelectPosition(-1);
            addBankListAdapter.notifyDataSetChanged();
        }
        setEmptyData();
    }

    @Override
    public void deleteData(int position) {
        banks.remove(position);

        if (addBankListAdapter != null) {
            addBankListAdapter.notifyDataSetChanged();
        }

        setEmptyData();

    }

    @Override
    public void clearData() {
        clearValue();
    }


    private void setEmptyData() {
        if (linearLayoutAddBank != null) {
            linearLayoutAddBank.setVisibility((banks.size() == 0) ? View.GONE : View.VISIBLE);
        }
    }

    @OnClick({R.id.imageViewIdentitiy, R.id.frameLayoutIdentifiy, R.id.chooseDateOfBirth})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageViewIdentitiy:
            case R.id.frameLayoutIdentifiy:
                presenter.imagePick(path -> {
                    linearLayoutPick.setVisibility(View.GONE);
                    selectImagePath = path;
                    Glide.with(getActivity()).load(selectImagePath).asBitmap().into(imageViewIdentitiy);
                });
                break;
            case R.id.chooseDateOfBirth:
                onClickDateOfBirthDay();
                break;
        }
    }

    public void onClickDateOfBirthDay() {
        birthDayCal = Calendar.getInstance();
        birthDayCal.set(Calendar.YEAR, birthDayCal.get(Calendar.YEAR));
        birthDayCal.add(Calendar.YEAR, -17);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear,
                                  int dayOfMonth) {
                birthDayCal.set(Calendar.YEAR, birthDayCal.get(Calendar.YEAR));
                if (!birthDayCal.after(Calendar.getInstance())) {
                    birthDayCal.set(Calendar.YEAR, year);
                    birthDayCal.set(Calendar.MONTH, monthOfYear);
                    birthDayCal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    chooseDateOfBirth.setText(new SimpleDateFormat("dd MMM, yyyy").format(birthDayCal.getTime()));
                    dateOfBirth = new SimpleDateFormat("dd MMM, yyyy").format(birthDayCal.getTime());
                } else {

                }
            }
        }, birthDayCal.get(Calendar.YEAR), birthDayCal.get(Calendar.MONTH), birthDayCal.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMaxDate(birthDayCal.getTime().getTime());
        datePickerDialog.show();
    }

    public void clearValue() {
        editTextFirstName.setText("");
        editTextLastName.setText("");
        editTextBankName.setText("");

        editTextAddress.setText("");
        chooseCountry.setText("");
        editTextZipCode.setText("");
        chooseDateOfBirth.setText("");

        selectImagePath = "";


        allEditTextClearData();


        selectPosition = 0;
        countryId = "";
        countrySelect = null;

        /*
        editTextRoutingNumber.setVisibility(View.GONE);
        editTextAccountNumber.setText("");
        editTextRoutingNumber.setText("");
*/

        Glide.with(getActivity()).load(R.drawable.shape_rounded_rectangle).asBitmap().into(imageViewIdentitiy);

        linearLayoutPick.setVisibility(View.VISIBLE);
        recycleraddBank.requestFocus();

        accountType = "";
        chooseAccountType.setText("");

        showPosition(0);
        editTextAdditionalInstructions.setText("");
        linearLayoutAccountTypeMain.setVisibility(View.GONE);


    }


    private void showPosition(int pos) {
        showAccountType();

        if (pos == 1) {
            modifyFromNumOne();
        }

        if (pos == 3) {
            modifyFromNum(pos);
        }
        linearLayout1.setVisibility((pos == 1) ? View.VISIBLE : View.GONE);
        linearLayout2.setVisibility((pos == 2) ? View.VISIBLE : View.GONE);
        linearLayout3.setVisibility((pos == 3) ? View.VISIBLE : View.GONE);
        linearLayout4.setVisibility((pos == 4) ? View.VISIBLE : View.GONE);
        linearLayout5.setVisibility((pos == 5) ? View.VISIBLE : View.GONE);
        linearLayout6.setVisibility((pos == 6) ? View.VISIBLE : View.GONE);
        linearLayout7.setVisibility((pos == 7) ? View.VISIBLE : View.GONE);
        linearLayout8.setVisibility((pos == 8) ? View.VISIBLE : View.GONE);
        linearLayout9.setVisibility((pos == 9) ? View.VISIBLE : View.GONE);
        linearLayout10.setVisibility((pos == 10) ? View.VISIBLE : View.GONE);
        linearLayout11.setVisibility((pos == 11) ? View.VISIBLE : View.GONE);
        linearLayout12.setVisibility((pos == 12) ? View.VISIBLE : View.GONE);
        linearLayout13.setVisibility((pos == 13) ? View.VISIBLE : View.GONE);
        linearLayout14.setVisibility((pos == 14) ? View.VISIBLE : View.GONE);
        linearLayout15.setVisibility((pos == 15) ? View.VISIBLE : View.GONE);
        linearLayout16.setVisibility((pos == 16) ? View.VISIBLE : View.GONE);
        linearLayout17.setVisibility((pos == 17) ? View.VISIBLE : View.GONE);

    }


    private void modifyFromNum(int pos) {
        if (countrySelect != null) {

            String bankCode = getResources().getString(R.string.bank_code);
            String branchCode = getResources().getString(R.string.branch_code);
            String accountNumber = getResources().getString(R.string.account_number);

            switch (countrySelect.getShortCode()) {
                case "BR":
                    bankCode = getString(R.string.bank_code);
                    branchCode = getString(R.string.branch_code_i_e_3826_1);
                    accountNumber = getString(R.string.account_number_i_e_98987689_1);
                    break;
                case "HK":
                    bankCode = getString(R.string.bank_code);
                    branchCode = getString(R.string.branch_code);
                    accountNumber = getString(R.string.account_number);
                    break;
                case "NZ":
                    bankCode = getString(R.string.bank_code_digits);
                    branchCode = getString(R.string.branch_code);
                    accountNumber = getString(R.string.account_number);
                    break;
            }

            editTextBankCode3.setHint(bankCode);
            editTextBranchCode3.setHint(branchCode);
            editTextNumber3.setHint(accountNumber);
        }
    }

    private void modifyFromNumOne() {
        if (countrySelect != null) {

            String iban = getResources().getString(R.string.iban_account_number);

            switch (countrySelect.getIsIban()) {
                case "1":
                    iban = getString(R.string.iban_account_number);
                    break;
                case "2":
                    iban = getString(R.string.account_number);
                    break;
                case "3":
                    iban = getString(R.string.iban_number);
                    break;
            }

            editTextNumber1.setHint(iban);
        }
    }

    private void showAccountType() {
        if (countrySelect != null) {


            switch (countrySelect.getShortCode()) {
                case "BR":
                case "US":
                case "PR":
                    linearLayoutAccountTypeMain.setVisibility(View.VISIBLE);
                    break;
                default:
                    linearLayoutAccountTypeMain.setVisibility(View.GONE);
                    break;
            }
        }
    }


    private Map<String, String> checkValidation(int pos) throws ApplicationException {
        Map<String, String> map = new HashMap<>();

        switch (pos) {
            case 1:
                validator.submit(editTextBicSwift1).checkEmpty().errorMessage(R.string.val_bank_bic_swift).check();


                validator.submit(editTextNumber1).checkEmpty().errorMessage(countrySelect.getIsIban().equalsIgnoreCase("1") ? R.string.val_bank_iban_account_number :
                        countrySelect.getIsIban().equalsIgnoreCase("2") ? R.string.val_bank_account_number :
                                countrySelect.getIsIban().equalsIgnoreCase("3") ? R.string.val_bank_iban : R.string.val_bank_iban_account_number).check();


                map.put(Common.BankFields.BIC_SWIFT, editTextBicSwift1.getText().toString().trim());

                String key = Common.BankFields.IBAN_ACCOUNT_NUMBER;

                if (countrySelect.getIsIban().equalsIgnoreCase("1")) {
                    key = Common.BankFields.IBAN_ACCOUNT_NUMBER;
                } else if (countrySelect.getIsIban().equalsIgnoreCase("2")) {
                    key = Common.BankFields.ACCOUNT_NUMBER;
                } else if (countrySelect.getIsIban().equalsIgnoreCase("3")) {
                    key = Common.BankFields.IBAN;
                }

                map.put(key, editTextNumber1.getText().toString().trim());

                break;
            case 2:
                validator.submit(editTextBsbBankId2).checkEmpty().errorMessage(R.string.val_bsb_bank_id_number).check();
                validator.submit(editTextBsbBranchId2).checkEmpty().errorMessage(R.string.val_bank_bcb_branch_id_number).check();
                validator.submit(editTextNumber2).checkEmpty().errorMessage(R.string.val_bank_account_number).check();

                map.put(Common.BankFields.BSB_BANK_ID, editTextBsbBankId2.getText().toString().trim());
                map.put(Common.BankFields.BSB_BRANCH_ID, editTextBsbBranchId2.getText().toString().trim());
                map.put(Common.BankFields.ACCOUNT_NUMBER, editTextNumber2.getText().toString().trim());

                break;
            case 3:
                validator.submit(editTextBankCode3).checkEmpty().errorMessage(R.string.val_bank_code).check();
                validator.submit(editTextBranchCode3).checkEmpty().errorMessage(R.string.val_bank_branch_code).check();
                validator.submit(editTextNumber3).checkEmpty().errorMessage(R.string.val_bank_account_number).check();

                map.put(Common.BankFields.BANK_CODE, editTextBankCode3.getText().toString().trim());
                map.put(Common.BankFields.BRANCH_CODE, editTextBranchCode3.getText().toString().trim());
                map.put(Common.BankFields.ACCOUNT_NUMBER, editTextNumber3.getText().toString().trim());

                break;
            case 4:
                validator.submit(editTextBankCode4).checkEmpty().errorMessage(R.string.val_bank_code).check();
                validator.submit(editTextBranchCode4).checkEmpty().errorMessage(R.string.val_trabsit_number).check();
                validator.submit(editTextNumber4).checkEmpty().errorMessage(R.string.val_bank_account_number).check();

                map.put(Common.BankFields.BANK_CODE, editTextBankCode4.getText().toString().trim());
                map.put(Common.BankFields.TRANSIT_NUMBER, editTextBranchCode4.getText().toString().trim());
                map.put(Common.BankFields.ACCOUNT_NUMBER, editTextNumber4.getText().toString().trim());

                break;
            case 5:
                validator.submit(editTextBranchName5).checkEmpty().errorMessage(R.string.val_bank_branch_name).check();
                validator.submit(editTextCity5).checkEmpty().errorMessage(R.string.val_bank_city).check();
                validator.submit(editTextProvince5).checkEmpty().errorMessage(R.string.val_bank_province).check();
                validator.submit(editTextNumber5).checkEmpty().errorMessage(R.string.val_bank_account_number).check();

                map.put(Common.BankFields.BRANCH_NAME, editTextBranchName5.getText().toString().trim());
                map.put(Common.BankFields.CITY, editTextCity5.getText().toString().trim());
                map.put(Common.BankFields.PROVINCE, editTextProvince5.getText().toString().trim());
                map.put(Common.BankFields.ACCOUNT_NUMBER, editTextNumber5.getText().toString().trim());

                break;
            case 6:
                validator.submit(editTextIfscCode6).checkEmpty().errorMessage(R.string.val_bank_ifsc_code).check();
                validator.submit(editTextNumber6).checkEmpty().errorMessage(R.string.val_bank_account_number).check();
                map.put(Common.BankFields.IFSC_CODE, editTextIfscCode6.getText().toString().trim());
                map.put(Common.BankFields.ACCOUNT_NUMBER, editTextNumber6.getText().toString().trim());
                break;
            case 7:
                validator.submit(editTextNumber7).checkEmpty().errorMessage(R.string.val_bank_account_number).check();
                validator.submit(editTextBranchName7).checkEmpty().errorMessage(R.string.val_bank_branch_name).check();
                map.put(Common.BankFields.ACCOUNT_NUMBER, editTextNumber7.getText().toString().trim());
                map.put(Common.BankFields.BRANCH_NAME, editTextBranchName7.getText().toString().trim());
                break;
            case 8:
                validator.submit(editTextBankCode8).checkEmpty().errorMessage(R.string.val_bank_code).check();
                validator.submit(editTextBranchCode8).checkEmpty().errorMessage(R.string.val_bank_branch_code).check();
                validator.submit(editTextNumber8).checkEmpty().errorMessage(R.string.val_bank_account_number).check();

                map.put(Common.BankFields.BANK_CODE, editTextBankCode8.getText().toString().trim());
                map.put(Common.BankFields.BRANCH_CODE, editTextBranchCode8.getText().toString().trim());
                map.put(Common.BankFields.ACCOUNT_NUMBER, editTextNumber8.getText().toString().trim());

                break;
            case 9:
                validator.submit(editTextBicSwift9).checkEmpty().errorMessage(R.string.val_bank_bic_swift).check();
                validator.submit(editTextBankBranchCode9).checkEmpty().errorMessage(R.string.val_bank_code_branch_code).check();
                validator.submit(editTextNumber9).checkEmpty().errorMessage(R.string.val_bank_account_number).check();

                map.put(Common.BankFields.BIC_SWIFT, editTextBicSwift9.getText().toString().trim());
                map.put(Common.BankFields.BANK_CODE_BRANCH_CODE, editTextBankBranchCode9.getText().toString().trim());
                map.put(Common.BankFields.ACCOUNT_NUMBER, editTextNumber9.getText().toString().trim());
                break;
            case 10:
                validator.submit(editTextClabe10).checkEmpty().errorMessage(R.string.val_bank_clabe_number).check();
                map.put(Common.BankFields.CLABE_NUMBER, editTextClabe10.getText().toString().trim());
                break;
            case 11:
                validator.submit(editTextAccountNumber11).checkEmpty().errorMessage(R.string.val_bank_account_number).check();
                map.put(Common.BankFields.ACCOUNT_NUMBER, editTextAccountNumber11.getText().toString().trim());
                break;
            case 12:
                validator.submit(editTextBankCode12).checkEmpty().errorMessage(R.string.val_bank_code).check();
                validator.submit(editTextNumber12).checkEmpty().errorMessage(R.string.val_bank_account_number).check();

                validator.submit(chooseAccountType12).checkEmpty().errorMessage(R.string.val_add_bank_account_type).check();

                map.put(Common.BankFields.BANK_CODE, editTextBankCode12.getText().toString().trim());
                map.put(Common.BankFields.ACCOUNT_NUMBER, editTextNumber12.getText().toString().trim());

                map.put(Common.BankFields.ACCOUNT_TYPE, chooseAccountType12.getText().toString().trim());

                map.put(Common.BankFields.TAX_ID, editTextTaxId12.getText().toString().trim());

                break;
            case 13:
                validator.submit(editTextBicSwift13).checkEmpty().errorMessage(R.string.val_bank_bic_swift).check();
                validator.submit(editTextBankCode13).checkEmpty().errorMessage(R.string.val_bank_code).check();
                validator.submit(editTextNumber13).checkEmpty().errorMessage(R.string.val_bank_account_number).check();

                map.put(Common.BankFields.BIC_SWIFT, editTextBicSwift13.getText().toString().trim());
                map.put(Common.BankFields.BANK_CODE, editTextBankCode13.getText().toString().trim());
                map.put(Common.BankFields.ACCOUNT_NUMBER, editTextNumber13.getText().toString().trim());

                break;
            case 14:
                validator.submit(editTextBicSwift14).checkEmpty().errorMessage(R.string.val_bank_bic_swift).check();
                validator.submit(editTextRoutingNumber14).checkEmpty().errorMessage(R.string.val_bank_routing_number).check();
                validator.submit(editTextNumber14).checkEmpty().errorMessage(R.string.val_bank_account_number).check();

                map.put(Common.BankFields.BIC_SWIFT, editTextBicSwift14.getText().toString().trim());
                map.put(Common.BankFields.ROUTING_NUMBER, editTextRoutingNumber14.getText().toString().trim());
                map.put(Common.BankFields.ACCOUNT_NUMBER, editTextNumber14.getText().toString().trim());

                break;
            case 15:
                validator.submit(editTextBranchSortingCode15).checkEmpty().errorMessage(R.string.val_bank_branch_sorting_code).check();
                validator.submit(editTextNumber15).checkEmpty().errorMessage(R.string.val_bank_account_number).check();
                validator.submit(editTextBuildingSocietyAccount15).checkEmpty().errorMessage(R.string.val_bank_building_society_account).check();

                map.put(Common.BankFields.BRANCH_SORTING_CODE, editTextBranchSortingCode15.getText().toString().trim());
                map.put(Common.BankFields.ACCOUNT_NUMBER, editTextNumber15.getText().toString().trim());
                map.put(Common.BankFields.BUILDING_SOCIRTY_CODE, editTextBuildingSocietyAccount15.getText().toString().trim());

                break;
            case 16:
                validator.submit(editTextNumber16).checkEmpty().errorMessage(R.string.val_bank_account_number).check();
                validator.submit(editTextRoutingNumber16).checkEmpty().errorMessage(R.string.val_bank_routing_number).check();
                map.put(Common.BankFields.ACCOUNT_NUMBER, editTextNumber16.getText().toString().trim());
                map.put(Common.BankFields.ROUTING_NUMBER, editTextRoutingNumber16.getText().toString().trim());
                break;
            case 17:
                validator.submit(editTextBicSwift17).checkEmpty().errorMessage(R.string.val_bank_bic_swift).check();
                validator.submit(editTextNumber17).checkEmpty().errorMessage(R.string.val_bank_account_number).check();
                map.put(Common.BankFields.BIC_SWIFT, editTextBicSwift17.getText().toString().trim());
                map.put(Common.BankFields.ACCOUNT_NUMBER, editTextNumber17.getText().toString().trim());
                break;
        }

        return map;
    }

    private void allEditTextClearData() {

        editTextBicSwift1.setText("");
        editTextNumber1.setText("");

        editTextBsbBankId2.setText("");
        editTextBsbBranchId2.setText("");
        editTextNumber2.setText("");

        editTextBankCode3.setText("");
        editTextBranchCode3.setText("");
        editTextNumber3.setText("");

        editTextBankCode4.setText("");
        editTextBranchCode4.setText("");
        editTextNumber4.setText("");

        editTextBranchName5.setText("");
        editTextCity5.setText("");
        editTextProvince5.setText("");
        editTextNumber5.setText("");

        editTextIfscCode6.setText("");
        editTextNumber6.setText("");

        editTextNumber7.setText("");
        editTextBranchName7.setText("");

        editTextBankCode8.setText("");
        editTextBranchCode8.setText("");
        editTextNumber8.setText("");

        editTextBicSwift9.setText("");
        editTextBankBranchCode9.setText("");
        editTextNumber9.setText("");

        editTextClabe10.setText("");

        editTextAccountNumber11.setText("");

        editTextBankCode12.setText("");
        editTextNumber12.setText("");

        //Set Additional
        chooseAccountType12.setText("");
        accountType12 = "";
        editTextTaxId12.setText("");


        editTextBicSwift13.setText("");
        editTextBankCode13.setText("");
        editTextNumber13.setText("");


        editTextBicSwift14.setText("");
        editTextRoutingNumber14.setText("");
        editTextNumber14.setText("");

        editTextBranchSortingCode15.setText("");
        editTextNumber15.setText("");
        editTextBuildingSocietyAccount15.setText("");

        editTextNumber16.setText("");
        editTextRoutingNumber16.setText("");

        editTextBicSwift17.setText("");
        editTextNumber17.setText("");

    }

}
