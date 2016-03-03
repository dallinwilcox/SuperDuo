package barqsoft.footballscores.service;

import android.annotation.TargetApi;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.content.ContentResolverCompat;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.text.SimpleDateFormat;
import java.util.Date;

import barqsoft.footballscores.DatabaseContract;
import barqsoft.footballscores.R;
import barqsoft.footballscores.Utilies;
import barqsoft.footballscores.scoresAdapter;

/**
 * Created by dcwilcox on 2/15/2016.
 * Referenced android StackWidget Sample at
 * https://github.com/android/platform_development/tree/master/samples/StackWidget
 * */


public class ScoreWidgetService extends RemoteViewsService{

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ScoreListRemoteViewsFactory(this.getApplicationContext(), intent);
    }

    class ScoreListRemoteViewsFactory implements RemoteViewsFactory {
        private Context context;
        private int appWidgetId;
        private Cursor cursor;
        private int scoreCount;
        public ScoreListRemoteViewsFactory(Context context, Intent intent)
        {
            this.context = context;
            appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }
        @Override
        public void onCreate() {
            Log.d("ScoreWidgetService", "created");
            SimpleDateFormat mformat = new SimpleDateFormat("yyyy-MM-dd");
            String[] currentDate = new String[1];
            currentDate[0] = mformat.format(new Date(System.currentTimeMillis()));
            cursor  = ContentResolverCompat.query(context.getContentResolver(),DatabaseContract.scores_table.buildScoreWithDate(),null,null,currentDate,null,null);
            scoreCount = cursor.getCount();
        }

        @Override
        public void onDataSetChanged() {

        }

        @Override
        public void onDestroy() {
            if (null != cursor){cursor.close();}
        }

        @Override
        public int getCount() {
            return scoreCount;
        }

        @Override
        public RemoteViews getViewAt(int position) {
            Log.d("ScoreWidgetService", "getViewAt: " + position);
            cursor.moveToPosition(position);
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.scores_list_item);
            Log.d("ScoreWidgetService", "setting home_name to " + cursor.getString(scoresAdapter.COL_HOME));
            remoteViews.setTextViewText(R.id.home_name, cursor.getString(scoresAdapter.COL_HOME));
            remoteViews.setTextViewText(R.id.away_name, cursor.getString(scoresAdapter.COL_AWAY));
            remoteViews.setTextViewText(R.id.score_textview, Utilies.getScores(cursor.getInt(scoresAdapter.COL_HOME_GOALS),cursor.getInt(scoresAdapter.COL_AWAY_GOALS)));
            remoteViews.setTextViewText(R.id.data_textview, cursor.getString(scoresAdapter.COL_MATCHTIME));
            return remoteViews;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }
}
