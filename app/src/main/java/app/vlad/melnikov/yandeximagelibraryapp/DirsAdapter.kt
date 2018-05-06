package app.vlad.melnikov.yandeximagelibraryapp

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.holder_dir.view.*

class DirsAdapter(val context: Context, private var list: ArrayList<Folder>, val clickListener: (Folder) -> Unit) : RecyclerView.Adapter<DirsAdapter.ViewHolder>() {

    var mSelected: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return DirsAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.holder_dir, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: DirsAdapter.ViewHolder, position: Int) {
        holder.bind(context, list.get(holder.adapterPosition))
        holder.card.setOnClickListener({
            clickListener(list.get(holder.adapterPosition))
        })
    }

    fun addItem(dir: Folder) {
        list.add(list.size, dir)
        notifyItemInserted(list.size - 1)
    }

    fun addItemAt(folder: Folder, pos: Int) {
        list.get(pos - 1).hasChild = true
        notifyItemChanged(pos - 1)
        list.add(pos, folder)
        list.get(pos).lvl = list.get(pos - 1).lvl + 1
        notifyItemInserted(pos)
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val card = view.card
        val dirName = view.text_folder_name
        val arrowIcon = view.ic_arrow
        val folderIcon = view.ic_folder
        val params = folderIcon.layoutParams as ConstraintLayout.LayoutParams

        fun bind(context: Context, dir: Folder) {
            dirName.text = dir.name

            if (dir.hasChild) {
                arrowIcon.visibility = View.VISIBLE
            } else {
                arrowIcon.visibility = View.GONE
            }

            if (dir.isChild) {
                params.marginStart = dir.lvl * 32
                if (dir.hasChild) {
                    folderIcon.background = ContextCompat.getDrawable(context, R.mipmap.ic_folder_black_24dp)
                } else {
                    folderIcon.background = ContextCompat.getDrawable(context, R.mipmap.ic_folder_open_black_24dp)
                }
            }
        }
    }
}