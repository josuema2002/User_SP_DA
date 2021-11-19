package com.example.users_sp_da

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.users_sp_da.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainActivity : AppCompatActivity(), OnClickListener {

    private lateinit var userAdapter: UserAdapter
    private lateinit var linearLayoutManager: RecyclerView.LayoutManager

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val preferences = getPreferences(Context.MODE_PRIVATE)

        val isFirstTime = preferences.getBoolean(getString(R.string.sp_first_time),true)
        Log.i("SP","${getString(R.string.sp_first_time)} = $isFirstTime")

        if(isFirstTime) {
            MaterialAlertDialogBuilder(this)
                .setTitle(R.string.dialog_title)
                .setPositiveButton(R.string.dialog_confirm, DialogInterface.OnClickListener { dialogInterface, i ->
                    preferences.edit().putBoolean(getString(R.string.sp_first_time), false).commit()
                })
                .setNegativeButton("Cancelar",null)
                .show()
        }

        userAdapter = UserAdapter(getUsers(), this)
        linearLayoutManager = LinearLayoutManager(this)

        binding.recyclerView.apply{
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
            adapter = userAdapter

        }
    }

    private  fun getUsers(): MutableList<User>{
        val users = mutableListOf<User>()

        val josue = User(1, "Josue", "Muñoz","data:image/jpeg;base64,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")
        val luis = User(2, "Luis", "Perez", "data:image/jpeg;base64,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")
        val javier = User(3, "Javier", "Gomez", "data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAoHCBYWFRgWFhUYGBgYGBkaGhwaGhgYGRoYGBgcGhoYGBgcIS4lHB4rIRoaJjgmKy8xNTU1GiQ7QDs0Py40NTEBDAwMEA8QHhISHjQrJSs2NDQ0NDQ0NDQ0NDQ0MTQ0NDQ0NDQ0NDQ0NDQ0NDQ0NDQ0NDQ0NDQ0NDQ0NDQ0NDQ0NP/AABEIAOEA4QMBIgACEQEDEQH/xAAbAAABBQEBAAAAAAAAAAAAAAADAAIEBQYBB//EAEAQAAIBAgMFBQUECQQCAwAAAAECAAMRBBIhBTFBUXEGImGBkTKhscHRE0Jy8AcUI1JikrLC4TRTgqIV8TNzk//EABkBAAMBAQEAAAAAAAAAAAAAAAABAgMEBf/EACcRAAICAgIBBAIDAQEAAAAAAAABAhEDEiExQSIyUWEEcTNCgSMT/9oADAMBAAIRAxEAPwCUouI9RBgadNYYSyWOpnU+XzkkHS8BQ3ny+ckb4MSHqI5REOESRAOtCKIwQqwAdaMYfGEtGsN3WDKOAZlysAw5H5cpU43YCt3qZ1/dPyMukE6yRapisxqrVotpmUjeOf1l5gNvK1hUGU8+EtHRXFnUMPeOhlPtDYFwWpm/hxk04j7L1SDqCDBYkd09DMlh8VVoNbWw+6Ze4bbKVFIJytbcZSkmKhbH0DdTLhDaVGzABe3My2pG8a6EEzwtM3Ejult0fh2jKJDRAxRQA6pjS0QjWgA8GOgxHiABVMfeCQx94mCHRRXiiAwtt86v1nEGhhANZSJYSloT+ecKBGIIYDSAIesSrYzojogOiESDEKsYDgY226PtGOdREwHoYUi8ptq7bpYZM9RueVRqzHkBMRiO39eo2WmuT8FifN20HoIroqrPTAtog1jPK6u1qxGZ61ReY+0a3uIHpIB26S1g7MfxM0HIep7DVoo4s6g+PESi2l2fI79M5hy4zCYbtNVU92o1uWYmbLYfahXslRwCdA26/gwGh6jdJ4Y9Ruz9oPTbvL3ffNNg8Yjjunyg6ezEqA66+/1lZidj1KRupJty3+Ygm0TRo7GdVbSgwO22Xu1B5y/o11cXU3lppgGQzpEaojzGAy8URiaACUx1xGCAqqSdIATFhQIFRuhwYmB2KKKIDDgwi74NIRd4lEsNTGsMDaDp74UiABBOqJwToiA6IRYIR6mMB5Mz/aztEmEQWGaq4ORP725KPfNDPEe1G0jicS7/AHL5Uv8AuLoD57/OJspIgYvF1a7l6jM7HiSbAclG4DwEKMRkFlFz+d8KtMIn3Ln+I39JXOCx090hlIJVuxu5Jv6ekGpClSN9ze3K+lvKW2ztg163soSOfCabZ3YGpvdR05iLZFKLZgalXW8LTrHnxt9D+ec3+P7A2BK/WZbFdm6iX7h09Ot4bIHBlnsHtbVpFCWLAEKyk+0BoOjbtfAT1TA7WWq3dN7i+vLTQjnrPAWpspAI3H4TZdlca4dMtyQyg7zfQ3HibC1uNoLsR6ji9nI43WPOU74CrRN0JI93pL3C4gOoYceYsR4EHcZIvfxlOPwRZU4HbQPdcZTLdXBFwbyvxuy0fcLGVmWrQPFlhbXYUaQzgEhYHaiOLHQ+MncNJSdgciQzhnFjJDLCiCpwkTGh952DiiGYsGEC38+ECdxhgZQmHp7/ACEMYFT7obNrAQRGjgIMQogB0CdAnLxywATpfTgd88Q7XYFKOJemlwqheN7nKCT5/We4Tyf9JuECYlXA0emCeN2VmBPplkyKRlsDSZ3CC5J0npWxOyiIqlhmJte8yfYlAavCerYYaWE58kn0dOKCq2WOz8KiABVAHpLVEErsMeEm2MI9FyBVlEr8Xh1I1UGTqiGRcSNDJkOJkdqdnqLksRbppMtsammHxShgSc/7M31HXmPzoDeehVFuCfCYHtbTWy899/GPFOpUTmgnGz0paqqwI0z8jdbgaeo0v4ASdTeYHsLiWbIjnN3iVJJJA+8tuoB8hN8UnVdnG1Qe95xlvvF4DUQ17iMRW4vZKtqmjeEh08VVomzi68x85erHvTVhZhIcfKKsjYbGI40MkFZU4rZFjmpnKfd5iDobTdDlqC3jwgpfIUX1OFvI+GxCuLgwzCUxIdFGxRDMW0KovaMMfSlCYemdYcSOg18odTeDEgimEWCWEUwA7ePWDj0gA4TB/pWw4+zovxDsnky5v7BN4sw/bit+sYZ1AsyOrKCPaAJUsD0JkSklwzSMZStrwZbsTSJqE8APeZ6lhDcTFdhsOBQZzxY+gtJGJ2xVucpVEHP5ndMJK2dEXrE9DwzgSZ9uDPGMT2hxDG6VtDxQM27fra00/Z3bTuArMXPPcbnmI36UCezPQGqjnIeJdTx0lJtXFPSW7aX3ekwGO2nVZ7pUqeOVSw36bj+bRJbDb15PRsQRwI1mF7W0roTyMr6ePc2tWJN9MwZDpvtcazQ4TDtiaLq3thT56aX8YtalwPbaLsgfo7qE4hU4KpcniO6Vt53HpPUCdZ5r2OZMNXcVAwIWwy2P3r634ajj9J6YJ0Ra6OWcXSfgcVjCtoQiIrLMxipCGIRQAHeDq4dXFmEOwnEETVlFM+znpm9M6cju8uUk4XauuVxlPj8payLi8CjjUSGmuh2P/XE5xSr/APD+J9TFC2PgqSsch1iB09I680IYZDr5Q94FBrCW3QEFQxxjUEcsAHCOvGGdBgAQ7jM7t+mq0mUpmJWwANtbWGvUzQAwbIC1mUHitxcXA3ddx9ZjkjdM6cEu0ZfszhcuHRCNdcw3WNzceEmY3sulSzHMwH3b2X0EdgWC1KicnYjo5z/3TUYJwRM/7Gq6MmmwlD51TvdVte+htY9essdn7HSkwOWx/PKaVlUcvdK1tXjkwjFLogdoqS1Cit0tIB2MqoyBAyvqw58PS3u0lp2gUCzHhw4yVswh0BI4cZKbTKlFNGUq9n1cBSgCqbi+8acAJb9n9n/Zud+6wltXpDWQcBiP2wQa3P8AmFtyQpJKLSKna9BBXcLzBPUf4mrwQ/Zpf9xfhMm/7XENlBAd7DpfU+k2oXgJeNXJszzOoRj/AKKdtO2nGnQcxyKJROiADWjlnGnQYAPE40SmNqnSJiQswnJFzRSRmbWP5RgOsIupEskOh4wivBgR6CABc3GP4QSQiwAcYgY0tOrAAiida1jcEjjbfpqCLRLOkxNWqKjJp2ZTFjJiMwVgrr3b3BJB1Njrbd69JdYPFEAC8qu0+KQPRTN37M2Xjk07x5ajTnrykIYxmR1BOa1hzt4cROaUafB1QlfZpqGJ+0YnNdV003E+JlfiO0OSrkNJhl0zaFT4i2szmDxlTUZHCroMoZtN25Re9vhHmmHGrlTzZHFuA4co0i9m+kWW2+0gYqLXAsTY3JtLPYm1w4Jyldb68rAazD43ZiK1/tle3ibX467v/cNg9pCl7Jz6cDpflb0iaDZrtG+x2JIEqNk1Cal7m5BtY2PlKtNrZgwy5QbEcLX36ekm9nWvVXxv/n4GKMXsTOXHBf7DwZBLurAjRc2/X2jb3S9WAzQiGdMUoqkcspOTthhE05edlCOLOxLFABrQNW9tIciMgAqB01g8S9hOGpaVe0MaADrJYD/1jxilH+vrziiGFA1hETUQY3wobdLIDLvj4NPp84S0ACII/NBq0Q1gUPYgxAwZji4G/SAJN9BwZC2ztNKCF2sTY5V4seXTmZT7f2w1PLkcA5rlQAQUtuYkXGtt3jMdj8Y9Vy7tcn0A5AcBEzohgfcgdLPXxJdiS7LUbTjkRmCAcBZbSxweLPdcECx/mU7r+NoPs24XF4dju+0Cno4Kf3Qm0cB+qVmS10BYEcQODDytJnFa2OXE6Xwa7Yjo9O62G4MPHmZJxuOekNEBBvr4+MzXZbayK2TgSDvAGt5tnyOm64P5tMNaZUZWigTaDVbDIDfe1hbTfpHVaaqDoCT4fASzRERe4oAG/wBfGU22cQoXu7+J+nj9ImrZW1IzOPq2YgHje/58ZbdkcUBVBbcQ6rv19m/e56jTxEytasWZtxv10tf8+kvqTkbOWqPao4hKmmhyVQabAH8WT0msI82ZbK1ZedsMdjKFnouDT490F16k7xM7gP0hYkHv5HH4bfCX1baQxGGzLrcd4HfPOMXRCty1tNJcdG2iq6PT8H29RrZ0t0MvsN2mw7j2iJ4zhzLKk1olJlf+MJeD12jtqizZQ8sFa+oNxPH6DkagzY9mNu3OR/I/IyyMn40auJr7wbx4MHVOkDiaohYh5ktuVZf42vvmO2rUJYyW+RorPtJ2Myzk0Io20eo1EG28R4bvDp8DJQElN85WrKvtMBfdcgX6SNi8SER3P3Vv1NtB5meeYnGO7l2YlidT9OQg3Rvhxbcvo9IO0qS6FxccNZGq7aQezr8JgquJLZSTra1+fKCesRxk7HSsEEb47UZvZtKzHbRcH2jfxOkzuF2g6HffwhNsYkOgcaMu7w5iVfBeqXSA4rE53Jvfx5+MFeRMPXDa8RvHL/EliSUmmuBBypDDepDDqpuJ6D2swoqBK6i61EB87D4g+6eeOdJ6d2fp/rGzUW92RSB4FGIHwE0UdotHNneskzzXEYd0bOmmv585Ow3ax0GU3GnHn5+UtsRgs1xuIuGHiNLSnxGzcp0UDy+Fpzp+GS4+UOftVv72+x4b7/8AqVlTaruMq3N+PAHmTwklsKvK2nID5Tq4TTl+d5haFrJkFVyoTx/xNl2ZwRq7Pq0rXL4dso/jQCon/ZBMrtNbJlHHQdToJ6FsnEDB4Z6xCkU1QgEkAhsoC6cdbAcTNcKu2yMvppI832bi3QXU6HgILHd9wd2/hvvxg6TgE5RZbmw32W+gJ46SStMsdBe3KZ8vg7VVcjKCW4ydSgFQjeCOsPR4Soo0jXgmoNIbB4jK4MjhrLeBV9eoMsqz1DYm0M6lTrbd0krGVrCYrs3tELUAJ0Ok0uPqjUXtE/o4M8KlZUYmvcmUe0Rxk+qwBPekHElTxkJMxtFVmnJK+xXnOzTkg0t7GdapYjp85FqVDfykbE19wisZB7R7Q7uQcSL9B/mx8plWeWG1nu56SCoifLO3DGooVNwRadqNoIx6fEToa/n8REa2LPxhA9xpvHvkdDwnFfK3gYxWd+yF7gWPhpcciNxkxXgPz5HdCLAaVBCZ6P8AowxQNOpSP3XuOjj6qZ5ys1n6O8TlxLJ++l/NCD8CZpjfqox/IjcGaDtRgfs3FUaKxCvbg25W+XpKWolxPRdp4ZatJgwuCNfPl4zzZUZHam+9TbXiODDrM88Odl/v7McE7Wr8ESpSHGR6i8pNxNOCCaTms3oz2JUvWpIut3XT8JufhNF+kfGlcPRoqTZyHY20ZU7qKDxsbk+UJ2M2dnxNSoRcU0spI0zMbk9QB/2jf0iU1ZVVNVph2J5s1rnpZRO/FH/n+ziyy9ZicPJtMSNh00EmUxOc9GK4DhibXJPU3ncPu6Ej3zibxO0PvDkx+spWaddHce9k6m0QFrfhMDtNtEH8Q+MKx1HQ/KPyJvkNhsQQQeUvdsYxmpJUBIv3GsfvDd6j4TNhuAl5s6n9ph61LiAtRPxLfTzFxGjPNHaLRn6mLe/tH1ME1Zj94+sE8aDLPMDZ25++KCzeMUANtVqa2kWo/wA45m70hY+pYctJmjQo8Y93br8IFI0mOWI9CKpUEvBMBw6x15xmgMA+hnKguJ1t3SNRoEB0a4U8xb6QogKQ7pHLUQym+vP5wKQVDJWDcrUQh2S7qpZGKsAxsbMNRvkNGjytyBzIHqZUXUkE1cGj1PA0amEruuepVpOq3DszsLXsyFjoRc3HHyELtTB0cRZ0dc+4G4B/Cy7xr4aayywT56FGo3tBQrdRoffeCxnZ9HbNbWdbUZcM8hScXaMbiaJXusDdeEARoZp+0mz1pUka5Pfy6kk2ysbDw0mcpISbDiQB4k7p5eSGs6R6WOe0NmWex6ZTDhQLGozOx/hvlUeeX3eMh9o8NcZbb0t8frNJhcOGfT2VKqOigKPcIPa2HH2iabyR8J6kFSUfo86T2k2eSUU0HSHRYTEUsruv7ruv8rEfKdpicVUz148pBaaQVH23H8Q/pEkqNJGQ/tag8V/pEZT8Efax9jqI9H73/H4kSPtl93UQWHqEuQOQEPJDfqLPDrrLPYm0Up1hmOjWX38ZR1MWE7q95jwHzncJRYHOx7x48FHG3Mwsd3wiw7R7JNCpcHMj5ih5WOqHxHwlMwmn2ztD7fDiykCm62vv7wIJPU2mdKTRHn5o6ypALRQmSKMyNY+8GVW1X7p85YM/vlDtmuQQALjWYo1grkiEs7aRipO9T5MRHrSI3Mw8DZhEjuTfwHJjGM5dhv16fSCd4wbOZ9TOE2MCamsdmuIEWSsM2p8Y9NLjkfcdfrI+CbWSW9rqPhr9YFJ8WOUwoexB5EH3wK751o0N9HtfZqrnpvT5ajzH1Bl3QuyjmB8Jk9gVcmRh0bzH+JsKR+8h1O8Gdc+OTyTG/pCcqlAc3Y/yrb+6UeyjerT/ABp8RrNB222ZiaposiFwhqZgg9ktly6cu7KLZqMmIRWUqyuLqRYjKCToek4cibyJ/o7cTSxtfs2ez0CoSP3jbytI23Ut9m38ck0/YVOOR2Pnbf6wfaXRU8GWeguzh8HmXaWhkxVYc3zfzqH+LGV9FZpO3NC2JVv36SHzUsp9wEoKazkkqkz18LuCf0EA0kBXtWqf8f6RJ5Eqar2q1PL+kSWaSfQDaTgg+7139JG2erMTZsoJ1PH/ABFXF81+N4TZy6AqQTyY219Il2YPmRb4fDIg3a8SdSZ1qyneRIwSod4B6MJ1qF96H89JRsuuDWbC+wrU2pF1V2BAB0zcRY8wZSvgACVJ1BIPUaSmfCldVuJIbFPVGV7iqB3W/ft9x+Z5N5RqXg5s+Ny5XZM/UR+9FKL9Zfx98Uqzj0Zqq7246TNYmqWckc9OkstsVrCw46eXGVQExOvDHjYSE8YQNGk2jGqHgIze6Ckc4CqRGOrHjAOjDjAmUvoFVWxvCI8DVPrOUWgY3yTcEe+ekkYlrEHkbyPgvaPQQ2JMDVe0MDOkwVN+6Onw0j6WrKObAeptGuym+D1bsx3lbwYe681P22UXvM/svZzo113NrbrLU4Z9xndKmeQGO1m3CVOPpmpWp1fvIbN4oQ1r9CfQmWtHZxO+FqYCwuPH4TNqL4KUmuSLgAW+1bkhUdBOdpx3VH8Qjtm1VWg5JAJDG3HXdObfBKg/xAyl7hPozH6RMNZMPU8XpnzAZf6WmNVp6d27wubAMeNMpUHkQrf9WaeVI0559np/iy9FfBIvKPFN+2fr8hLkGZ/aDftX/F8hMpG03wMd79JI2VTBEhFpJ2Zh7i9+OkImSfqLpKZtBvQPAkRyKw43jiTKNyMFqLqrGFXaA9mvTHg6DK6nnpo3QwoYzjANow0iFRN/WaH+8v8A+bRSB/49PzeKAtfog46vnfwGg+sEpjFjpJEVSo7YTkU4XEChMYGownXcQDgHjBGcmCrJyMDROsVZCIykdYznb9RYbPPeaHxBgsAN5jqzawN17RIe75n6ybspM1akOdRP6hIKHQ9Zb9l1zYql4Nf0B+dpUVbQpOotnuaU7KpG8AfCTKFQNvGsio+gEcy8RvnS1Z5pZZRA4092Mw+I4Gc2ie4deEhL1JDvgpKWKpZGAXUIt9D7QNraiSNqnOhFukgUDcsODX/7i495kfHYp7Cw4e/jNoxqhNkral6uGdC3tIy+608uw+ycSyqy4esVbcRTcg+dp6Js2izuPtCRTU68Mx4L05zUU8dTY5c1uVtZz55xUtUdn420Yt0eVYLsljHt+xKD95yqAdR7XumN7R7OfD4ipTqABgQbg3VlYaMp4gz2ztBtd6V+6xUagqpbToNRPF+1eKqVsQ1V0dVsqrdWACgaC9t9yfWc6k5OqN5ydJsp3bSSdm1DYjxkBnl5g9mVClN0p1GJzhgqMxsDdWsBexBOvhLUTOMrkSKZPMwhxRHC8J+qVQNaTjxKOAPUaTiJYakR0dSYlxXMQlOvTPtEr7/dAVMTTXeyyO2KpHTKT0Un5QDb7Ln9j/vD+UxSmsn+3U/lb6RQDZfJFSOiimaEDaDaKKUSwNSRzFFEZyGvuMEm+KKMwfZZ4DcY598UUDoXtQ1Nx6j5y97Hf6pPP4iKKXDtEZPYz26lwh1iinSeeMG+d2t7Aiik/wBkHgocH7f/ABT+lY/Hez/yb+qKKbkvoIv+m/5CWHZj2X/FFFPKzfyM9PF/EX8cNxiilRIfRXVf/kPSYPtx/qKX/wBi/GKKdGMmPuNEm/1+E8u7fe2/UfCKKSzr8MxuA3meh9luEUUSIh0bKKKKUM//2Q==")
        val emma =  User(4, "Emma", "Cruz", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT7wqH1_R9gj9cvaQwi5sGo70WkEIUuF9JoUQ&usqp=CAU")


        users.add(josue)
        users.add(luis)
        users.add(javier)
        users.add(emma)
        users.add(josue)
        users.add(luis)
        users.add(javier)
        users.add(emma)
        users.add(josue)
        users.add(luis)
        users.add(javier)
        users.add(emma)

        return users
    }

    override fun onClick(user: User, position: Int) {
        Toast.makeText(this, "$position: ${user.getFullName()}", Toast.LENGTH_SHORT).show()
    }
}