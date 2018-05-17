package zhengc.bcit.ca.benehome;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Document extends Fragment {

    private List<String> listDataHeader;
    private HashMap<String, List<String>> listHash;
    View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @SuppressLint("InflateParams")
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.activity_document,null);

        final ExpandableListView listView = view.findViewById(R.id.listExp);
        initData();
        ExpandableListAdapter listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listHash);
        listView.setAdapter(listAdapter);

        listView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousGroup = -1;

            @Override
            public void onGroupExpand(int groupPosition) {
                if(groupPosition != previousGroup)
                    listView.collapseGroup(previousGroup);
                previousGroup = groupPosition;
            }
        });

        return view;
    }

    private void initData() {
        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();

        listDataHeader.add("Identification and proof of status in Canada for all household members");
        listDataHeader.add("Proof of current address and rent:");
        listDataHeader.add("Proof of income and assets:");
        listDataHeader.add("Proof of full-time student status for dependent children aged 19-24:");
        listDataHeader.add("Copy of repayment agreement (if money is owed to previous subsidized housing):");
        listDataHeader.add("Copy of Notice to End Tenancy:");

        List<String> faq1 = new ArrayList<>();
        faq1.add("For each family member born in Canada: a copy of Canadian birth certificate");
        faq1.add("For each family member not born in Canada: a copy of citizenship or immigration document." +
                "\nAcceptable documents include:" +
                "\n\nCanadian Citizenship Card (if date issued is more than 8 years ago); or\n\n" +
                "Permanent Resident Card (both sides); or\n" +
                "Record of Landing (IMM1000); or\n\n" +
                "Sponsorship Undertaking: Confirmation of Permanent Residence (IMM5292); or\n\n" +
                "IMM5617 - Verification of Landing (if landing date is more than 10 years ago).");

        List<String> faq2 = new ArrayList<>();
        faq2.add("Copy of current rent receipt or recent rent increase notice; or");
        faq2.add("Copy of lease or tenancy agreement showing current rent amount.");

        List<String> faq3 = new ArrayList<>();
        faq3.add("If receiving income assistance from the Ministry of Social Development and Social Innovation:" +
                "\n\nCopy of income assistance cheque stub; or"+
                "\n\nLetter from your MSDSI worker confirming monthly income assistance amount");
        faq3.add("If employed:" +
                "\n\nLast three consecutive pay stubs showing gross monthly income; or"+
                "\n\nLetter from employer confirming gross monthly income");
        faq3.add("If other income source(s): Copies of cheque stubs, banks statements showing direct deposit of pensions, or other confirmation of income");
        faq3.add("If you own property: Property tax assessments for value of property owned and proof of outstanding mortgage(s)");
        faq3.add("If you have assets other than property: Copies of bank statements or letter from financial institution stating total asset value.");

        List<String> faq4 = new ArrayList<>();
        faq4.add("Proof of full-time attendance or enrollment at a school, university or vocational institution which provides a recognized diploma, certificate or degree"
                + "\n\n\n\nNote: Must be under age 25. Full-time attendance means enrolment in the minimum of credit courses specified by the institution to be considered full-time, or if not specified by the institution, enrolment in a minimum of 15 hours of classroom instruction per week in each school term.");

        List<String> faq5 = new ArrayList<>();
        faq5.add("Repayment agreement(s) you have with your past landlord(s).");

        List<String> faq6 = new ArrayList<>();
        faq6.add("If you have received a legal Notice to End Tenancy, a copy of all pages of the Notice to End Tenancy. This must be the official form from the Residential Tenancy Branch (RTB). To get a copy of this form, call the RTB at 604 660-3456 or download it from www.rto.gov.bc.ca.");

        listHash.put(listDataHeader.get(0), faq1);
        listHash.put(listDataHeader.get(1), faq2);
        listHash.put(listDataHeader.get(2), faq3);
        listHash.put(listDataHeader.get(3), faq4);
        listHash.put(listDataHeader.get(4), faq5);
        listHash.put(listDataHeader.get(5), faq6);

    }
}
