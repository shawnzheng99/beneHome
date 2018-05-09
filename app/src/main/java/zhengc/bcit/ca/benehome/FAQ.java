package zhengc.bcit.ca.benehome;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FAQ extends Fragment {

    private static final String TAG = House_detail.class.getName();
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listHash;
    View view;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.activity_faq,null);

        final ExpandableListView listView = view.findViewById(R.id.lvExp);
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

        listDataHeader.add("1.Who is considered a dependent child?");
        listDataHeader.add("2.When will I receive an offer for subsidized housing?");
        listDataHeader.add("3.What are the residency requirements?");
        listDataHeader.add("4.How will I be contacted?");
        listDataHeader.add("5.How much will I pay");
        listDataHeader.add("6.Do I need to provide a landlord reference?");
        listDataHeader.add("7.Can I have a pet?");


        List<String> faq1 = new ArrayList<>();
        faq1.add("An unmarried child, stepchild, adopted child or legal ward, mainly supported by the applicant, who is:\n\n" +
                "Under 19 years of age; or \n\n" +
                "Under 25 years of age and registered in full-time school, university or vocational institute which" +
                "provides a recognized diploma, certificate, or degree; or\n\n" +
                "Of any age who, because of mental or physical infirmity, is accepted as a dependent for income tax purposes.");
        List<String> faq2 = new ArrayList<>();
        faq2.add("The demand for subsidized housing far exceeds the available supply. As a result," +
                "it is not possible to predict when a unit may come available. Wait times depend on the number" +
                "of unit turnovers and the needs of other households applying for housing." +
                "To increase your chances of obtaining a unit, select a range of developments" +
                "with The Housing Registry and apply directly to developments that are not part of the registry.");

        List<String> faq3 = new ArrayList<>();
        faq3.add("Applicants must permanently reside in British Columbia when applying, and each member of the household must be one of the following:\n" +
                "\n" +
                "Canadian citizen\n\n" +
                "Individual lawfully admitted into Canada for permanent residence\n\n" +
                "Refugee sponsored by the Government of Canada\n\n" +
                "Individual who has applied for refugee status");

        List<String> faq4 = new ArrayList<>();
        faq4.add("When you are being considered for a vacant unit, the housing provider will call you for more information." +
                "At this time, the housing provider will perform additional validations and checks to determine if they will offer you an available unit." +
                "Each housing provider will have their own process for reviewing and evaluating applications for possible tenancies.");

        List<String> faq5 = new ArrayList<>();
        faq5.add("If you are offered a rent-geared-to-income unit, the amount you will pay depends on the size of your family and your gross household income." +
                "If you are offered a low-end of market unit, you will pay an amount set at, or slightly below, private market rents." +
                "View information on rent-geared-to-income and market housing. Some housing developments may have other charges." +
                "View the Housing Listings for notes about other charges or expenses.");

        List<String> faq6 = new ArrayList<>();
        faq6.add("Most housing providers perform landlord reference checks when they are reviewing applicants for future available units." +
                "Applicants, who have been identified as potentially not eligible due to an unsatisfactory tenancy history," +
                "will be asked to provide a current and verifiable landlord reference(s) demonstrating that they have maintained a successful tenancy" +
                "with no reoccurrence of the behaviours demonstrated in past tenancies.");

        List<String> faq7 = new ArrayList<>();
        faq7.add("Each housing provider has its own pet policy and if they allow pets, there may be restrictions on both the types and number of pets you can have.");

        listHash.put(listDataHeader.get(0), faq1);
        listHash.put(listDataHeader.get(1), faq2);
        listHash.put(listDataHeader.get(2), faq3);
        listHash.put(listDataHeader.get(3), faq4);
        listHash.put(listDataHeader.get(4), faq5);
        listHash.put(listDataHeader.get(5), faq6);
        listHash.put(listDataHeader.get(6), faq7);

    }
}
