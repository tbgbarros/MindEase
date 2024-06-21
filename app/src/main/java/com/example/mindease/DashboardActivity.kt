package com.example.mindease

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.utils.ColorTemplate

class DashboardActivity : AppCompatActivity() {

    private lateinit var textViewDiasTreinados: TextView
    private lateinit var barChartMeditacao: BarChart
    private lateinit var buttonMeditacao: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        textViewDiasTreinados = findViewById(R.id.textViewDiasTreinados)
        barChartMeditacao = findViewById(R.id.barChartMeditacao)
        buttonMeditacao = findViewById(R.id.buttonMeditacao)

        // Configurar dados fictícios
        textViewDiasTreinados.text = "Dias Treinados: 5"

        val entries = ArrayList<BarEntry>()
        entries.add(BarEntry(0f, 30f))
        entries.add(BarEntry(1f, 20f))
        entries.add(BarEntry(2f, 50f))
        entries.add(BarEntry(3f, 10f))
        entries.add(BarEntry(4f, 60f))

        val dataSet = BarDataSet(entries, "Minutos de Meditação")
        dataSet.colors = ColorTemplate.COLORFUL_COLORS.toList()

        val data = BarData(dataSet)
        barChartMeditacao.data = data
        barChartMeditacao.description.isEnabled = false
        barChartMeditacao.animateY(1000)

        val xAxis = barChartMeditacao.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)

        barChartMeditacao.axisLeft.setDrawGridLines(false)
        barChartMeditacao.axisRight.setDrawGridLines(false)
        barChartMeditacao.invalidate()

        // Navegar para a página de meditação
        buttonMeditacao.setOnClickListener {
            val intent = Intent(this, MeditacaoActivity::class.java)
            startActivity(intent)
        }
    }
}