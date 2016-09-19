package com.pindroid.action;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;

import com.pindroid.Constants;
import com.pindroid.R;
import com.pindroid.activity.Main;

public class IntentHelper {
	
	public static Intent SendBookmark(String url, String title) {
    	Intent sendIntent = new Intent(Intent.ACTION_SEND);
    	sendIntent.setType("text/plain");
    	sendIntent.putExtra(Intent.EXTRA_TEXT, url);
    	sendIntent.putExtra(Intent.EXTRA_SUBJECT, title);
    	sendIntent.putExtra(Intent.EXTRA_TITLE, title);
    	
    	return sendIntent;
	}
	
	public static Intent AddBookmark(String url, String account, Context context) {
		Intent addBookmark = new Intent(context, Main.class);
		addBookmark.setAction(Intent.ACTION_SEND);
		if(url != null)
			addBookmark.putExtra(Intent.EXTRA_TEXT, url);
		
		addBookmark.putExtra(Constants.EXTRA_INTERNAL, true);
		Uri.Builder data = new Uri.Builder();
		data.scheme(Constants.CONTENT_SCHEME);
		data.encodedAuthority((account != null ? account + "@" : "") + Constants.INTENT_URI);
		data.appendEncodedPath("bookmarks");
		addBookmark.setData(data.build());
		
		return addBookmark;
	}
	
	public static Intent ViewBookmarks(String tag, String account, String feed, Context context) {
		Intent i = new Intent(context, Main.class);
		i.setAction(Intent.ACTION_VIEW);
		i.addCategory(Intent.CATEGORY_DEFAULT);
		Uri.Builder data = new Uri.Builder();
		data.scheme(Constants.CONTENT_SCHEME);
		data.encodedAuthority((account != null ? account + "@" : "") + Constants.INTENT_URI);
		data.appendEncodedPath("bookmarks");
		
		if(tag != null && !tag.equals(""))
			data.appendQueryParameter("tagname", tag);
		
		if(feed != null && !feed.equals(""))
			data.appendQueryParameter("feed", feed);
		
		i.setData(data.build());
		
		return i;
	}
	
	public static Intent ViewUnread(String account, Context context) {
		Intent i = new Intent(context, Main.class);
		i.setAction(Intent.ACTION_VIEW);
		i.addCategory(Intent.CATEGORY_DEFAULT);
		Uri.Builder data = new Uri.Builder();
		data.scheme(Constants.CONTENT_SCHEME);
		data.encodedAuthority((account != null ? account + "@" : "") + Constants.INTENT_URI);
		data.appendEncodedPath("bookmarks");
		data.appendQueryParameter("unread", "1");
		i.setData(data.build());
		
		return i;
	}
	
	public static Intent ViewNotes(String account, Context context) {
		Intent i = new Intent(context, Main.class);
		i.setAction(Intent.ACTION_VIEW);
		i.addCategory(Intent.CATEGORY_DEFAULT);
		Uri.Builder data = new Uri.Builder();
		data.scheme(Constants.CONTENT_SCHEME);
		data.encodedAuthority((account != null ? account + "@" : "") + Constants.INTENT_URI);
		data.appendEncodedPath("notes");
		i.setData(data.build());
		
		return i;
	}
	
	public static Intent WidgetSearch(String account, Context context){
		Intent i = new Intent(context, Main.class);
		i.setAction(Intent.ACTION_SEARCH);
		Uri.Builder data = new Uri.Builder();
		data.scheme(Constants.CONTENT_SCHEME);
		data.encodedAuthority((account != null ? account + "@" : "") + Constants.INTENT_URI);
		data.appendEncodedPath("search");
		i.setData(data.build());
		
		return i;
	}

    public static void openInChromeCustomTab(Activity activity, String url){
		CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
		builder.setShowTitle(true);
		builder.addDefaultShareMenuItem();
		builder.setToolbarColor(activity.getResources().getColor(R.color.actionbar_background));
		builder.setStartAnimations(activity, R.anim.slide_up, R.anim.hold);
		builder.setExitAnimations(activity, R.anim.hold, R.anim.slide_down);
		CustomTabsIntent customTabsIntent = builder.build();
		customTabsIntent.launchUrl(activity, Uri.parse(url));
    }
}
