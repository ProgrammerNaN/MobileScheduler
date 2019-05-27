package fragments;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.teleg.programm.DBControllers.NewsDB;
import com.example.teleg.programm.R;
import com.example.teleg.programm.serverSide.Api.ClientXml;
import com.example.teleg.programm.serverSide.Api.RsreuApi;
import com.example.teleg.programm.serverSide.News.ItemNews;
import com.example.teleg.programm.serverSide.News.RVAdapter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class NewsFragment extends Fragment  {

    private Button button;
    private RecyclerView rv;
    private NewsDB newsDb;

    //private RsreuApi api = ClientXml.getInstance().getApi();




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

        newsDb = new NewsDB(getContext());

        List<ItemNews> news = new ArrayList<>();


        SQLiteDatabase database = newsDb.getWritableDatabase();

        Cursor cursor = database.query(NewsDB.TABLE_NEWS, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            int titleIndex = cursor.getColumnIndex(NewsDB.KEY_TITLE);
            int descriptionIndex = cursor.getColumnIndex(NewsDB.KEY_DESCRIPTION);
            int dataIndex = cursor.getColumnIndex(NewsDB.KEY_DATE);
            do {
                ItemNews item = new ItemNews();
                item.setTitle(cursor.getString(titleIndex));
                item.setDescription(cursor.getString(descriptionIndex));
                item.setPubDate(cursor.getString(dataIndex));

                news.add(item);
            } while (cursor.moveToNext());
        } else {
            Log.d("База данных", "Пустотааааа");
        }

        RVAdapter adapter = new RVAdapter(news);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        rv.setLayoutManager(manager);
        rv.setAdapter(adapter);

       /** int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
           // loadNews();
        }*/

        return view;
    }

    /**public void loadNews() {
        api.getItems()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(feed -> {
                    List<ItemNews> news = feed.getChannel().getNewsList();
                    RVAdapter adapter = new RVAdapter(news);
                    LinearLayoutManager manager = new LinearLayoutManager(getContext());
                    rv.setLayoutManager(manager);
                    rv.setAdapter(adapter);
                }, Throwable::printStackTrace);
    }*/

}