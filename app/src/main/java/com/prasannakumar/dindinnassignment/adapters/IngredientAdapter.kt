package com.prasannakumar.dindinnassignment.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.prasannakumar.dindinnassignment.R
import com.prasannakumar.dindinnassignment.dataClass.ProductList


class IngredientAdapter(ingredientList: ArrayList<ProductList>) :
    RecyclerView.Adapter<IngredientAdapter.ViewHolder>() {

    private var courseModalArrayList: ArrayList<ProductList>


    fun filterList(filterList: ArrayList<ProductList>) {

        courseModalArrayList = filterList

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.ingredient_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: ProductList = courseModalArrayList[position]
        holder.courseNameTV.text = model.productName
        holder.courseDescTV.text = model.productID
        Glide.with(holder.itemView)
            .load(model.image.replace("http", "https"))
            .centerCrop()
            .placeholder(R.drawable.monkey)
            .error(R.drawable.ic_broken_image)
            .fallback(R.drawable.ic_no_image)
            .transform()
            .into(holder.foodImage)

    }

    override fun getItemCount(): Int {
        return courseModalArrayList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val courseNameTV: TextView = itemView.findViewById(R.id.idTVCourseName)
        val courseDescTV: TextView = itemView.findViewById(R.id.idTVCourseDescription)
        val foodImage: ImageView = itemView.findViewById(R.id.img_food_image)

    }

    init {
        this.courseModalArrayList = ingredientList

    }
}