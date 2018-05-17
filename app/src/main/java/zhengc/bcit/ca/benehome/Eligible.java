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

public class Eligible extends Fragment {

    private List<String> listDataHeader;
    private HashMap<String, List<String>> listHash;
    View view;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @SuppressLint("InflateParams")
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.activity_eligible,null);

        final ExpandableListView listView = view.findViewById(R.id.lvExp_eligible);
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

        listDataHeader.add("a) You must fall into one of the eligible groups");
        listDataHeader.add("b) You must meet the residency requirements");
        listDataHeader.add("c) Your household income must be below certain limits");
        listDataHeader.add("d) Your assets may be subject to certain requirements");
        listDataHeader.add("e) You do not meet any of the factors for exclusion");
        listDataHeader.add("f) If you are a former subsidized housing tenant, you must meet certain criteria");


        List<String> faq1 = new ArrayList<>();
        faq1.add("> Family – Defined as a minimum of two people, including one dependent child. Who is considered a dependent child? An unmarried child, stepchild, adopted child or legal ward, mainly supported by the applicant, who is under 19 years of age; or under 25 years of age and registered in full-time school, university or vocational institute which provides a recognized diploma, certificate, or degree; or of any age who, because of mental or physical infirmity, is accepted as a dependent for income tax purposes.\n\n" +
                "> Senior – Defined as a single person age 55 and older, or a couple where at least one person is age 55 or older. (*Some housing providers using the Housing Registry may use a different age to define a senior.)\n\n" +
                "> People with disabilities – Those who can live independently and are in receipt of a recognized disability pension or are considered disabled for income tax purposes.\n\n" +
                "> Single people and couples – You are a single person, or a couple, with a low income and homeless, or at risk of homelessness. In addition, you do not meet the definitions of seniors or people with disabilities.");
        List<String> faq2 = new ArrayList<>();
        faq2.add("Applicants must permanently reside in British Columbia when applying, and each member of the household must be one of the following:\n\n" +
                "    > Canadian citizen\n" +
                "    > Individual lawfully admitted into Canada for permanent residence\n" +
                "    > Refugee sponsored by the Government of Canada\n" +
                "    > Individual who has applied for refugee status\n\n" +
                "No adult members of the household can be under private sponsorship, except where BC Housing has accepted that private sponsorship has broken down.");

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

        listHash.put(listDataHeader.get(0), faq1);
        listHash.put(listDataHeader.get(1), faq2);
        listHash.put(listDataHeader.get(2), faq3);
        listHash.put(listDataHeader.get(3), faq4);
        listHash.put(listDataHeader.get(4), faq5);
        listHash.put(listDataHeader.get(5), faq6);

    }
}
