/*
 * Copyright (c) 2018. Jahir Fiquitiva
 *
 * Licensed under the CreativeCommons Attribution-ShareAlike
 * 4.0 International License. You may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 *    http://creativecommons.org/licenses/by-sa/4.0/legalcode
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jahirfiquitiva.chipview

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.jahirfiquitiva.chip.ChipView

class MainActivity : AppCompatActivity() {
    
    private val chip: ChipView? by lazy {
        findViewById<ChipView?>(R.id.chip)
    }
    
    private val iconChip: ChipView? by lazy {
        findViewById<ChipView?>(R.id.icon_chip)
    }
    
    private val actionChip: ChipView? by lazy {
        findViewById<ChipView?>(R.id.action_chip)
    }
    
    private val strokeChip: ChipView? by lazy {
        findViewById<ChipView?>(R.id.stroke_chip)
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        chip?.setOnClickListener { showToast("You pressed the basic chip") }
        
        iconChip?.setOnClickListener { showToast("You pressed the chip with icon") }
        
        actionChip?.setOnClickListener { showToast("You pressed the chip with action icon") }
        actionChip?.setOnActionClickListener { showToast("You pressed the action icon") }
        
        strokeChip?.setOnClickListener { showToast("You pressed the stroked chip") }
    }
    
    private fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
}