package com.jahirfiquitiva.chipview

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
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
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        chip?.setOnClickListener { showToast("You pressed the basic chip") }
        
        iconChip?.setOnClickListener { showToast("You pressed the chip with icon") }
        
        actionChip?.setOnClickListener { showToast("You pressed the chip with action icon") }
        actionChip?.setOnActionClickListener { showToast("You pressed the action icon") }
    }
    
    private fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
}