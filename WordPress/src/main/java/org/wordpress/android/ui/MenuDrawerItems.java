package org.wordpress.android.ui;

import android.content.Context;

import org.wordpress.android.R;
import org.wordpress.android.WordPress;
import org.wordpress.android.datasets.CommentTable;
import org.wordpress.android.models.Blog;
import org.wordpress.android.ui.comments.CommentsActivity;
import org.wordpress.android.ui.media.MediaBrowserActivity;
import org.wordpress.android.ui.notifications.NotificationsActivity;
import org.wordpress.android.ui.posts.PagesActivity;
import org.wordpress.android.ui.posts.PostsActivity;
import org.wordpress.android.ui.reader.ReaderPostListActivity;
import org.wordpress.android.ui.stats.StatsActivity;
import org.wordpress.android.ui.themes.ThemeBrowserActivity;

import java.util.ArrayList;

public class MenuDrawerItems {

    public enum DrawerItemId {
        READER,
        NOTIFICATIONS,
        POSTS,
        MEDIA,
        PAGES,
        COMMENTS,
        THEMES,
        STATS,
        VIEW_SITE,
        QUICK_PHOTO,
        QUICK_VIDEO;

        public ActivityId toActivityId() {
            switch (this) {
                case READER:
                    return ActivityId.READER;
                case NOTIFICATIONS:
                    return ActivityId.NOTIFICATIONS;
                case POSTS:
                    return ActivityId.POSTS;
                case MEDIA:
                    return ActivityId.MEDIA;
                case PAGES:
                    return ActivityId.PAGES;
                case COMMENTS:
                    return ActivityId.COMMENTS;
                case THEMES:
                    return ActivityId.THEMES;
                case STATS:
                    return ActivityId.STATS;
                case VIEW_SITE:
                    return ActivityId.VIEW_SITE;
                case QUICK_PHOTO:
                    return ActivityId.UNKNOWN;
                case QUICK_VIDEO:
                    return ActivityId.UNKNOWN;
                default :
                    return ActivityId.UNKNOWN;
            }
        }
    }

    /*
     *
     */
    public static class DrawerItem {
        private final DrawerItemId mItemId;

        public DrawerItem(DrawerItemId itemId) {
            mItemId = itemId;
        }

        public DrawerItemId getDrawerItemId() {
            return mItemId;
        }

        public int getTitleResId() {
            switch (mItemId) {
                case READER:
                    return R.string.reader;
                case NOTIFICATIONS:
                    return R.string.notifications;
                case POSTS:
                    return R.string.posts;
                case MEDIA:
                    return R.string.media;
                case PAGES:
                    return R.string.pages;
                case COMMENTS:
                    return R.string.tab_comments;
                case THEMES:
                    return R.string.themes;
                case STATS:
                    return R.string.tab_stats;
                case VIEW_SITE:
                    return R.string.view_site;
                case QUICK_PHOTO:
                    return R.string.quick_photo;
                case QUICK_VIDEO:
                    return R.string.quick_video;
                default :
                    return 0;
            }
        }

        public int getIconResId() {
            switch (mItemId) {
                case READER:
                    return R.drawable.noticon_reader_alt_black;
                case NOTIFICATIONS:
                    return R.drawable.noticon_notification_black;
                case POSTS:
                    return R.drawable.dashicon_admin_post_black;
                case MEDIA:
                    return R.drawable.dashicon_admin_media_black;
                case PAGES:
                    return R.drawable.dashicon_admin_page_black;
                case COMMENTS:
                    return R.drawable.dashicon_admin_comments_black;
                case THEMES:
                    return R.drawable.dashboard_icon_themes;
                case STATS:
                    return R.drawable.noticon_milestone_black;
                case VIEW_SITE:
                    return R.drawable.noticon_show_black;
                case QUICK_PHOTO:
                    return R.drawable.dashicon_camera_black;
                case QUICK_VIDEO:
                    return R.drawable.dashicon_video_alt2_black;
                default :
                    return 0;
            }
        }

        public int getBadgeCount() {
            if (mItemId == DrawerItemId.COMMENTS) {
                return CommentTable.getUnmoderatedCommentCount(WordPress.getCurrentLocalTableBlogId());
            } else {
                return 0;
            }
        }

        public boolean isSelected(Context context) {
            switch (mItemId) {
                case READER:
                    return context instanceof ReaderPostListActivity;
                case NOTIFICATIONS:
                    return context instanceof NotificationsActivity;
                case POSTS:
                    return (context instanceof PostsActivity) && !(context instanceof PagesActivity);
                case MEDIA:
                    return context instanceof MediaBrowserActivity;
                case PAGES:
                    return context instanceof PagesActivity;
                case COMMENTS:
                    return context instanceof CommentsActivity;
                case THEMES:
                    return context instanceof ThemeBrowserActivity;
                case STATS:
                    return context instanceof StatsActivity;
                case VIEW_SITE:
                    return context instanceof ViewSiteActivity;
                case QUICK_PHOTO:
                    return false;
                case QUICK_VIDEO:
                    return false;
                default :
                    return false;
            }
        }

        public boolean isVisible() {
            switch (mItemId) {
                case READER:
                    return WordPress.hasValidWPComCredentials(WordPress.getContext());
                case NOTIFICATIONS:
                    return WordPress.hasValidWPComCredentials(WordPress.getContext());
                case POSTS:
                    return WordPress.wpDB.getNumVisibleAccounts() != 0;
                case MEDIA:
                    return WordPress.wpDB.getNumVisibleAccounts() != 0;
                case PAGES:
                    return WordPress.wpDB.getNumVisibleAccounts() != 0;
                case COMMENTS:
                    return WordPress.wpDB.getNumVisibleAccounts() != 0;
                case THEMES:
                    Blog blog = WordPress.getCurrentBlog();
                    return (blog != null && blog.isAdmin() && blog.isDotcomFlag());
                case STATS:
                    return WordPress.wpDB.getNumVisibleAccounts() != 0;
                case VIEW_SITE:
                    return WordPress.wpDB.getNumVisibleAccounts() != 0;
                case QUICK_PHOTO:
                    return WordPress.wpDB.getNumVisibleAccounts() != 0;
                case QUICK_VIDEO:
                    return WordPress.wpDB.getNumVisibleAccounts() != 0;
                default :
                    return false;
            }
        }
    }

    /*
     *
     */

    private final ArrayList<DrawerItem> mItems = new ArrayList<DrawerItem>();

    public void addItem(DrawerItemId itemId) {
        mItems.add(new DrawerItem(itemId));
    }

    public int size() {
        return mItems.size();
    }

    public DrawerItem get(int position) {
        return mItems.get(position);
    }

    public ArrayList<DrawerItem> getItems() {
        return mItems;
    }

    public MenuDrawerItems getVisibleItems() {
        MenuDrawerItems visibleItems = new MenuDrawerItems();
        for (MenuDrawerItems.DrawerItem item : mItems) {
            if (item.isVisible()) {
                visibleItems.addItem(item.getDrawerItemId());
            }
        }
        return visibleItems;
    }

}
