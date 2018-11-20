package com.kooloco.ui.myexperience.fragment;
/**
 * Created by hlink44 on 31/10/17.
 */

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.kooloco.R;
import com.kooloco.di.component.FragmentComponent;
import com.kooloco.model.BlogDetails;
import com.kooloco.model.BlogMedia;
import com.kooloco.model.ExperienceDetails;
import com.kooloco.ui.base.BaseFragment;
import com.kooloco.ui.myexperience.adapter.BlogListAdapter;
import com.kooloco.ui.myexperience.adapter.MyExperienceAdapter;
import com.kooloco.ui.myexperience.presenter.BlogListPresenter;
import com.kooloco.ui.myexperience.view.BlogListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class BlogListFragment extends BaseFragment<BlogListPresenter, BlogListView> implements BlogListView {

    @BindView(R.id.textViewNoData)
    AppCompatTextView textViewNoData;
    @BindView(R.id.recyclerViewBlog)
    RecyclerView recyclerViewBlog;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;

    private List<BlogDetails> data;

    BlogListAdapter blogListAdapter;


    //Pagination

    LinearLayoutManager mLayoutManager;
    int pastVisiblesItems, visibleItemCount, totalItemCount;

    boolean isLoading = false;

    int page = 1;


    @Override
    protected int createLayout() {
        return R.layout.fragment_blog_list;
    }

    @Override
    protected void inject(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    protected BlogListView createView() {
        return this;
    }

    @Override
    protected void bindData() {


        data = new ArrayList<>();

        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerViewBlog.setLayoutManager(mLayoutManager);
        blogListAdapter = new BlogListAdapter(getActivity(), getChildFragmentManager(), data, new BlogListAdapter.CallBack() {
            @Override
            public void onClickItem(int position) {
                presenter.openBlogDetails(data.get(position));
            }

            @Override
            public void onClickItemBlog(int position) {

            }

            @Override
            public void onClickVideo(int position, BlogMedia blogMedia) {
                if (blogMedia.getMediaType().equalsIgnoreCase("V")) {
                    playVideo(blogMedia.getFile());
                }
            }

            @Override
            public void onClickFav(int position, BlogDetails blogDetails) {
                presenter.setBlogLike(blogDetails.getId(), position);
            }

            @Override
            public void onClickImage(int position, BlogDetails blogDetails) {
                imageOpenZoom(blogDetails.getProfileImage());
            }
        });
        recyclerViewBlog.setAdapter(blogListAdapter);


        presenter.getDataLocal();

        recyclerViewBlog.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                visibleItemCount = mLayoutManager.getChildCount();
                totalItemCount = mLayoutManager.getItemCount();
                pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();
                if ((visibleItemCount + 3 + pastVisiblesItems) >= totalItemCount) {
                    if (isLoading) {
                        page = page + 1;
                        isLoading = false;
                        presenter.getData(page, false);
                    }
                }

            }
        });

        page = 1;
        presenter.getData(page, false);

        swipeRefresh.setColorSchemeColors(getActivity().getResources().getColor(R.color.colorPrimary), getActivity().getResources().getColor(R.color.yellow), getActivity().getResources().getColor(R.color.colorAccent));
        swipeRefresh.setOnRefreshListener(() -> {
            if (presenter != null) {
                page = 1;
                presenter.getData(page, false);
            }
        });


    }

    @OnClick(R.id.imageViewBack)
    public void onViewClicked() {
        goBack();
    }

    @Override
    public void setData(List<BlogDetails> dashData, int page) {
        if (page == 1) {
            data.clear();
        }

        data.addAll(dashData);

        if (swipeRefresh != null) {
            if (swipeRefresh.isRefreshing()) {
                swipeRefresh.setRefreshing(false);
            }
        }

        if (getActivity() != null) {
            if (blogListAdapter != null) {
                blogListAdapter.notifyDataSetChanged();
            }
        }

        if (textViewNoData != null) {
            textViewNoData.setVisibility((data.size() == 0) ? View.VISIBLE : View.INVISIBLE);
        }

        isLoading = !dashData.isEmpty();
    }

    @Override
    public void updateDataBlog(BlogDetails blogDetails, int position) {
        data.set(position, blogDetails);

        if (blogListAdapter != null) {
            blogListAdapter.notifyItemChanged(position);
        }
    }
}
