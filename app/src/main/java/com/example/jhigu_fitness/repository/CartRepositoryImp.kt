//package com.example.jhigu_fitness.repository
//
//import com.example.jhigu_fitness.model.CartModel
//import com.google.firebase.database.DataSnapshot
//import com.google.firebase.database.DatabaseError
//import com.google.firebase.database.DatabaseReference
//import com.google.firebase.database.FirebaseDatabase
//import com.google.firebase.database.ValueEventListener
//
//class CartRepositoryImp: CartRepository {
//
//
//    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
//
//    val ref : DatabaseReference = database.reference
//        .child("Cart")
//
//    override fun addCart(cartModel: CartModel, callback: (Boolean, String) -> Unit) {
//        var id = ref.push().key.toString()
//        cartModel.cartId = id
//
//        ref.child(id).setValue(cartModel).addOnCompleteListener {
//            if(it.isSuccessful){
//                callback(true,"Cart added successfully")
//            }else{
//                callback(false,"${it.exception?.message}")
//
//            }
//        }
//    }
//
//    override fun updateCart(
//        cartId: String,
//        data: MutableMap<String, Any>,
//        callback: (Boolean, String) -> Unit
//    ) {
//        ref.child(cartId).updateChildren(data).addOnCompleteListener {
//            if (it.isSuccessful) {
//                callback(true, "Cart updated successfully")
//            } else {
//                callback(false, "${it.exception?.message}")
//
//            }
//        }
//    }
//    override fun deleteCart(cartId: String, callback: (Boolean, String) -> Unit) {
//        ref.child(cartId).removeValue().addOnCompleteListener {
//            if(it.isSuccessful){
//                callback(true,"Cart deleted successfully")
//            }else{
//                callback(false,"${it.exception?.message}")
//            }
//        }
//    }
//
//    override fun getCartById(
//        cartId: String,
//        callback: (CartModel?, Boolean, String) -> Unit
//    ) {
//        ref.child(cartId).addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                if (snapshot.exists()) {
//                    var model = snapshot.getValue(CartModel::class.java)
//                    callback(model, true, "Cart fetched")
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                callback(null, false, error.message)
//            }
//        })
//    }
//
//    override fun getAllCart(callback: (List<CartModel>?, Boolean, String) -> Unit) {
//        ref.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                if (snapshot.exists()) {
//                    var cart = mutableListOf<CartModel>()
//                    for (eachData in snapshot.children) {
//                        var model = eachData.getValue(CartModel::class.java)
//                        if (model != null) {
//                            cart.add(model)
//                        }
//                    }
//
//                    callback(cart, true, "Cart fetched")
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                callback(null, false, error.message)
//            }
//        })
//    }
//}
