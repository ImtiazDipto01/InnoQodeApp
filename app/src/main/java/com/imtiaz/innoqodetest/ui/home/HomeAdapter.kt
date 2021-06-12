package com.imtiaz.innoqodetest.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.request.RequestOptions
import com.imtiaz.innoqodetest.R
import com.imtiaz.innoqodetest.data.local.entity.BlogPost
import com.imtiaz.innoqodetest.databinding.ItemBlogPostBinding
import com.imtiaz.innoqodetest.utils.DATE_TIME_FORMAT_12
import com.imtiaz.innoqodetest.utils.DATE_TIME_FORMAT_15
import com.imtiaz.innoqodetest.utils.getDateInRequestedFormat

class HomeAdapter(
    var categoryList: List<BlogPost>,
    val onClick: (BlogPost) -> Unit
) :
    RecyclerView.Adapter<HomeAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =
        MyViewHolder(
            ItemBlogPostBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = categoryList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) =
        holder.bind(categoryList[position])

    inner class MyViewHolder(binding: ItemBlogPostBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val _binding: ItemBlogPostBinding = binding

        init {
            itemView.setOnClickListener {
                onClick(categoryList[adapterPosition])
            }
        }

        fun bind(post: BlogPost) {
            _binding.apply {
                tvPostTitle.text = post.title ?: "Not Found"
                tvPostDate.text = getDateInRequestedFormat(
                    post.date ?: "",
                    DATE_TIME_FORMAT_15,
                    DATE_TIME_FORMAT_12
                )

                val options: RequestOptions = RequestOptions()
                    .transform(FitCenter())
                    .placeholder(R.drawable.ic_placeholder)
                    .error(R.drawable.ic_placeholder)
                    .priority(Priority.HIGH)

                Glide.with(ivPostBanner.context)
                    .load(post.imageUrl)
                    .apply(options)
                    .into(ivPostBanner)
            }
        }

    }
}