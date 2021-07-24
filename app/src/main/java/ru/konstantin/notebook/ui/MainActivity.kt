package ru.konstantin.notebook.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import ru.konstantin.notebook.R
import ru.konstantin.notebook.entity.Note
import ru.konstantin.notebook.ui.details.NoteDetailsFragment.Companion.newInstance
import ru.konstantin.notebook.ui.list.NoteListFragment.OnNoteClicked
import ru.konstantin.notebook.ui.menu.AboutAppFragment

class MainActivity : AppCompatActivity(), OnNoteClicked {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar, R.string.app_name, R.string.app_name
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        val navigationView = findViewById<NavigationView>(R.id.navigation_view)
        navigationView.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener { item ->
            drawerLayout.closeDrawer(GravityCompat.START)
            if (item.itemId == R.id.about_app) {
                supportFragmentManager
                    .beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.container, AboutAppFragment())
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            false
        })
    }

    override fun onNoteClicked(note: Note?) {
        val isLandscape = resources.getBoolean(R.bool.isLandscape)
        if (isLandscape) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.notes_details_fragment, newInstance(note))
                .commit()
        } else {
            val listFragment = supportFragmentManager.findFragmentById(R.id.container_list)
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, newInstance(note))
                .hide(listFragment!!)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_appbar_notes, menu)
        val searchItem = menu.findItem(R.id.option_search)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                Toast.makeText(this@MainActivity, query, Toast.LENGTH_SHORT).show()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.option_add) {
            Toast.makeText(this, "added note", Toast.LENGTH_SHORT).show()
            return true
        }
        return false
    }
}