/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.navigation

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ShareCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.android.navigation.databinding.FragmentGameWonBinding


class GameWonFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding: FragmentGameWonBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_game_won, container, false
        )

        binding.nextMatchButton.setOnClickListener {
            findNavController().navigate(R.id.action_gameWonFragment_to_gameFragment)
        }
        setHasOptionsMenu(true)
        return binding.root
    }

    @SuppressLint("QueryPermissionsNeeded")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.winner_menu, menu)
        // check if the activity resolves
        if (null == shareIntent().resolveActivity(requireActivity().packageManager)) {
            // hide the menu item if it doesn't resolve
            menu.findItem(R.id.share)?.isVisible = false
        }
    }

    private fun shareIntent(): Intent {
        val args = GameWonFragmentArgs.fromBundle(requireArguments())
        return ShareCompat.IntentBuilder.from(requireActivity())
            .setText(
                getString(
                    R.string.share_success_text,
                    args.numCorrect,
                    args.numQuestions
                )
            )
            .setType("text/plain")
            .intent

//        val intentShare = Intent(Intent.ACTION_SEND).apply {
//            type = "text/plain"
//            putExtra(
//                Intent.EXTRA_TEXT,
//                getString(R.string.share_success_text, args.numCorrect, args.numQuestions)
//            )
//        }
//        startActivity(intentShare)

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.share) {
            // Starting an Activity with our new Intent
            startActivity(shareIntent())
        }
        return super.onOptionsItemSelected(item)
    }

}
