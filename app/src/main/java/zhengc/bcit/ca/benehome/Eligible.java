package zhengc.bcit.ca.benehome;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Spinner;

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
        faq3.add("To be eligible for subsidized housing, the applicant's gross household income must be below certain income limits, as established by the Housing Income Limits (HILs). HILs are determined by BC Housing from time to time, and represent the income required to pay the average market rent for an appropriately sized unit in the private market. Average market rents are derived from Canada Mortgage and Housing Corporation’s Annual Rent Market Survey.\n\n" +
                "Exceptions:\n\n" +
                "    > Applicants from households with income over the HILs can be considered if the applicant is applying for developments with market rents or low end of market units.\n" +
                "    > The Senior's Rental Housing program has different income limits based on your location in the province. (Lower Mainland – $58,000)\n\n" +
                "Housing Income Limits (HILs) for major centres in British Columbia\n\n" +
                "Region: Vancouver\n" +
                "> Studio: $38,500\n" +
                "> 1 bedroom: $42,500\n" +
                "> 2 bedroom: $52,000\n" +
                "> 3 bedroom: $64,500\n" +
                "> 4 bedroom: $68,500");

        List<String> faq4 = new ArrayList<>();
        faq4.add("For buildings managed by BC Housing, in order to be eligible your household assets must be less than $100,000. BC Housing’s asset policy has been developed to benefit those in greatest need and to prevent people from having to deplete all of their resources.\n\n" +
                "Individual non-profit or co-operative housing providers will decide if they are going to apply an asset ceiling and the maximum allowable assets may vary by provider." +
                "Each housing provider will have their own process for reviewing and evaluating applications for possible tenancies.");

        List<String> faq5 = new ArrayList<>();
        faq5.add("Potential exclusion criteria – Applicants may be excluded from consideration for The Housing Registry if any of the following apply:\n\n" +
                "> Unsatisfactory tenancy history (based on landlord references, receipt of notices to end tenancies, or review of past tenancies in subsidized housing).\n\n" +
                "> Provision of false or fraudulent information.\n\n" +
                "> Failure to provide documents as requested, or consent as needed to verify information provided and to determine eligibility.\n\n" +
                "> Unable to demonstrate an ability to pay rent and/or failure to apply for and receive income from income programs, such as, but not limited to, the Ministry of Social Development or the Canada Pension Plan.\n\n" +
                "> Debt to a subsidized housing provider in B.C. (see section f)\n\n" +
                "> Do not meet residency requirements.\n\n" +
                "> Unable to live independently with supports.\n\n" +
                "> Do not meet defined household groups (as defined in section a).\n\n" +
                "> Deliberately worsened current housing situation.\n\n" +
                "> Demonstration of unacceptable behaviours either in relation to a tenancy or in the community at large that may threaten the health, safety or right of peaceful enjoyment of a community by others.\n\n" +
                "> If there is cause to believe that a household member is engaging in or has a history of criminal activity that may threaten the health, safety or right of peaceful enjoyment of the community by others, including the manufacturing or production of illegal drugs.\n\n" +
                "Note: Applicants who may be potentially excluded based on any of the above will be provided with an opportunity to provide additional information and/or documentation in support of their application to demonstrate suitability as a potential tenant. Each application and supporting documents shall be evaluated on its own merit.");

        List<String> faq6 = new ArrayList<>();
        faq6.add("Tenants who previously lived in subsidized housing may be eligible to reapply, provided they meet the residency, income limit and target group criteria, as well as the following special criteria:\n\n" +
                "> Applicants must have no outstanding debt from any previous tenancy with BC Housing or any members of the Housing Registry. Applicants with outstanding debt will be asked to pay in full, or enter into a repayment plan for the outstanding amount.\n\n" +
                "> Previous subsidized housing tenants may be subject to a file review. If the previous tenancy was ended for cause, applicants may not be eligible to reapply. Files will be reviewed on a case-by-case basis.");

        listHash.put(listDataHeader.get(0), faq1);
        listHash.put(listDataHeader.get(1), faq2);
        listHash.put(listDataHeader.get(2), faq3);
        listHash.put(listDataHeader.get(3), faq4);
        listHash.put(listDataHeader.get(4), faq5);
        listHash.put(listDataHeader.get(5), faq6);

    }
}
