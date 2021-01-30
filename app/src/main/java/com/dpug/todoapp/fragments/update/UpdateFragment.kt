 package com.dpug.todoapp.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dpug.todoapp.R
import com.dpug.todoapp.data.models.ToDoData
import com.dpug.todoapp.data.viewmodel.ToDoViewModel
import com.dpug.todoapp.databinding.FragmentUpdatedBinding
import com.dpug.todoapp.fragments.SharedViewModel

 class UpdateFragment : Fragment() {

    val args by navArgs<UpdateFragmentArgs>()

     private val mSharedViewModel: SharedViewModel by viewModels()
     private val mToDoViewModel: ToDoViewModel by viewModels()

     private var binding: FragmentUpdatedBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Data Binding
        binding = FragmentUpdatedBinding.inflate(inflater, container, false)
        binding?.sargs = args.currentItem
        // Set Menu
        setHasOptionsMenu(true)

        // Spinner Item Selected Listener
        binding?.spinnerPrioritiesCurrent?.onItemSelectedListener = mSharedViewModel.listener

        return binding?.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_fragment_menu, menu)
    }

     override fun onOptionsItemSelected(item: MenuItem): Boolean {
         when(item.itemId){
             R.id.menu_save -> updateItem()
             R.id.menu_delete -> confirmItemRemoval()
         }
         return super.onOptionsItemSelected(item)
     }

     private fun updateItem() {
         val description = binding?.etDescriptionCurrent?.text.toString()
         val title = binding?.etTitleCurrent?.text.toString()
         val getPriority = binding?.spinnerPrioritiesCurrent?.selectedItem.toString()

         val validation = mSharedViewModel.verifyDataFromUser(title, description)
         if(validation){
             // Update Current Item
             val updateItem = ToDoData(
                     args.currentItem.id,
                     title,
                     mSharedViewModel.parsePriority(getPriority),
                     description
             )
             mToDoViewModel.updateData(updateItem)
             Toast.makeText(requireContext(), "Обновлено", Toast.LENGTH_SHORT).show()
             //Navigate Back
             findNavController().navigate(R.id.action_updateFragment_to_listFragment)
         }else{
             Toast.makeText(requireContext(), "Заполните все поля!", Toast.LENGTH_SHORT).show()
         }
     }
     // Show AlertDialog to Confirm Removal
     private fun confirmItemRemoval() {
         val builder = AlertDialog.Builder(requireContext())
         builder.setPositiveButton("Да") { _,_ ->
             mToDoViewModel.deleteItem(args.currentItem)
             Toast.makeText(
                     requireContext(),
                     "Удалено: ${args.currentItem.title}",
                     Toast.LENGTH_SHORT
             ).show()
             findNavController().navigate(R.id.action_updateFragment_to_listFragment)
         }
         builder.setNegativeButton("Нет") { _,_ -> }
         builder.setTitle("Удалить '${args.currentItem.title}'?")
         builder.setMessage("Действительно удалить? '${args.currentItem.title}'?")
         builder.create().show()
     }

     override fun onDestroyView() {
         super.onDestroyView()
         binding = null
     }
 }