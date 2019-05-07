package fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.teleg.programm.R;
import com.example.teleg.programm.serverSide.News.Feed;
import com.example.teleg.programm.serverSide.News.ItemNews;
import com.example.teleg.programm.serverSide.News.RVAdapter;
import com.example.teleg.programm.serverSide.News.RssAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class NewsFragment extends Fragment implements Callback<Feed> {


    private Button button;
    private RecyclerView rv;
    public NewsFragment() {
    }

    public static NewsFragment newInstance() {
        return new NewsFragment();
    }

    static final String BASE_URL = "http://www.rsreu.ru/";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_news_list, container, false);
        rv = (RecyclerView)view.findViewById(R.id.recyclerView);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();
        RssAdapter rssAdapter = retrofit.create(RssAdapter.class);

        Call<Feed> call = rssAdapter.getItems();
        call.enqueue(this);


        //RecyclerView rv = (RecyclerView)getView().findViewById(R.id.recyclerView);
        //LinearLayoutManager manager = new LinearLayoutManager(getContext());
        //rv.setLayoutManager(manager);
        //rv.setAdapter(adapter);

        return view;
    }


    @Override
    public void onResponse(Call<Feed> call, Response<Feed> response) {
        try {

            if (response.isSuccessful()) {
                Feed feed = response.body();
                List<ItemNews> news = feed.getChannel().getNewsList();
                RVAdapter adapter = new RVAdapter(news);
                LinearLayoutManager manager = new LinearLayoutManager(getContext());
                rv.setLayoutManager(manager);
                rv.setAdapter(adapter);
            }
        }
        catch (Exception ex) {
            Toast.makeText(getActivity(),
                    "ошибка обработки данных",
                    Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onFailure(Call<Feed> call, Throwable t) {
        t.printStackTrace();
    }
}