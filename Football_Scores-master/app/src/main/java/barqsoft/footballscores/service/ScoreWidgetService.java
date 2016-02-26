package barqsoft.footballscores.service;

import android.annotation.TargetApi;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import barqsoft.footballscores.FootbalScoresAppWidgetProvider;
import barqsoft.footballscores.R;

/**
 * Created by dcwilcox on 2/15/2016.
 */
@TargetApi(11)
public class ScoreWidgetService extends RemoteViewsService{

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ScoreListRemoteViewsFactory(this.getApplicationContext(), intent);
    }

    class ScoreListRemoteViewsFactory implements RemoteViewsFactory {
        private Context context;
        private int appWidgetId;
        public ScoreListRemoteViewsFactory(Context context, Intent intent)
        {
            this.context = context;
            appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }
        @Override
        public void onCreate() {


        }

        @Override
        public void onDataSetChanged() {

        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public RemoteViews getViewAt(int position) {

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.scores_list_item);
            remoteViews.setTextViewText(R.id.home_name, "home_name");
            remoteViews.setTextViewText(R.id.away_name, "away_name");
            remoteViews.setTextViewText(R.id.score_textview, "score");
            remoteViews.setTextViewText(R.id.data_textview, "date");

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
