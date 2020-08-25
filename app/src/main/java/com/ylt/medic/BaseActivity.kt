package com.ylt.medic

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.facebook.stetho.Stetho
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * Created by yoannlt on 13/06/2017.
 */
abstract class BaseActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    protected lateinit var navigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Stetho.initializeWithDefaults(this);

        setContentView(contentViewId)

        navigationView = findViewById(R.id.navigation)
        navigationView.setOnNavigationItemSelectedListener(this)
    }

    override fun onStart() {
        super.onStart()
        updateNavigationBarState()
    }

    // Remove inter-activity transition to avoid screen tossing on tapping bottom navigation items
    public override fun onPause() {
        super.onPause()
        overridePendingTransition(0, 0)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        navigationView.postDelayed({
            val itemId = item.itemId
            if (itemId == R.id.navigation_search) {
                startActivity(Intent(this, SearchActivity::class.java))
            } else if (itemId == R.id.navigation_favorite) {
                startActivity(Intent(this, FavoriteActivity::class.java))
            } else if (itemId == R.id.navigation_about) {
                startActivity(Intent(this, AboutActivity::class.java))
            }
            finish()
        }, 100)
        return true
    }

    private fun updateNavigationBarState() {
        val actionId = navigationMenuItemId
        selectBottomNavigationBarItem(actionId)
    }

    private fun selectBottomNavigationBarItem(itemId: Int) {
        val menu = navigationView.getMenu()
        var i = 0
        val size = menu.size()
        while (i < size) {
            val item = menu.getItem(i)
            val shouldBeChecked = item.getItemId() == itemId
            if (shouldBeChecked) {
                item.isChecked = true
                break
            }
            i++
        }
    }

    internal abstract val contentViewId: Int

    internal abstract val navigationMenuItemId: Int

}