package app.vlad.melnikov.yandeximagelibraryapp

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Callback
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.holder_photo.view.*

class PhotoFeedAdapter(val context: Context, private var list: ArrayList<Photo>, val clickListener: (Photo) -> Unit) : RecyclerView.Adapter<PhotoFeedAdapter.ViewHolder>() {
    private var filteredList: ArrayList<Photo> = list

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.holder_photo, parent, false))
    }

    override fun getItemCount(): Int {
        return filteredList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(context, filteredList.get(position), clickListener)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val card = view.card
        val imageHolder = view.photo
        val progress = view.progressBar

        fun bind(context: Context, item: Photo, clickListener: (Photo) -> Unit) {
            progress.visibility = View.VISIBLE
            if (!item.loading) {
                Picasso.with(context)
                        .load(item.path)
                        .into(imageHolder, object : Callback {
                            override fun onSuccess() {
                                progress.visibility = View.GONE
                                card.setOnClickListener { clickListener(item) }
                            }

                            override fun onError() {
                                imageHolder.setBackgroundColor(Color.LTGRAY)
                            }
                        })

            }
        }
    }

    fun search(_text: String?) {
        val text = _text?.toLowerCase()
        if (text!!.isEmpty()) {
            filteredList = list
        } else {
            val newList = ArrayList<Photo>()
            for (photo in list) {
                if (photo.name.toLowerCase().contains(text.toString())) {
                    newList.add(photo)
                }
            }
            filteredList = newList
        }

        notifyDataSetChanged()
    }

    fun addItem(item: Photo) {
        filteredList.add(item)
        notifyItemInserted(filteredList.size - 1)
    }

    fun loadPhoto(position: Int?, link: Link?) {
        filteredList.get(position!!).path = link!!.href
        filteredList.get(position).loading = false
        notifyItemChanged(position)
    }

    fun clear() {
        list.clear()
        filteredList.clear()
        notifyDataSetChanged()
    }
}