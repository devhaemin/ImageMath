package com.haemin.imagemathtutor.QAFragmentMVP;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.haemin.imagemathtutor.Data.Question;
import com.haemin.imagemathtutor.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class QAFragment extends Fragment implements QAContract.QAView, SwipeRefreshLayout.OnRefreshListener {


    QAPresenter presenter;
    QuestionAdapter adapter;
    ArrayList<Question> questions;

    @BindView(R.id.recycler_qa)
    RecyclerView qaRecycler;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.text_no_question_data)
    TextView textNoQuestionData;

    public QAFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_qa, container, false);
        ButterKnife.bind(this, v);
        questions = new ArrayList<>();
        presenter = new QAPresenter(this);

        adapter = new QuestionAdapter(getContext(),questions);
        bindListener();
        qaRecycler.setAdapter(adapter);
        qaRecycler.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false));
        presenter.requestQuestionList();
        refreshLayout.setOnRefreshListener(this);
        return v;
    }

    @Override
    public void onRefresh() {
        presenter.requestQuestionList();
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void showToast(String text) {
        Toast.makeText(getContext(),text,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateRecycler(ArrayList<Question> questions) {
        if(questions.size() != 0){
         textNoQuestionData.setVisibility(View.GONE);

            this.questions.clear();
            this.questions.addAll(questions);
            adapter.notifyDataSetChanged();
        }else
        {
            showNoData();
        }
    }

    public void showNoData() {
        textNoQuestionData.setVisibility(View.VISIBLE);
    }
    @Override
    public void bindView() {
        adapter = new QuestionAdapter(getContext(),questions);
        qaRecycler.setAdapter(adapter);
        qaRecycler.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false));
    }

    @Override
    public void bindListener() {

    }
}
