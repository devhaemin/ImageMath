import Charts
import SwiftUI

struct TestChartView : UIViewRepresentable {
    
    
    
    //Bar chart accepts data as array of BarChartDataEntry objects
    var scoreEntries:[ChartDataEntry]
    var averageEntries:[ChartDataEntry]
    // this func is required to conform to UIViewRepresentable protocol
    func makeUIView(context: Context) -> LineChartView {
        //crate new chart
        let chart = LineChartView()
        //it is convenient to form chart data in a separate func
        chart.data = addData()
        chart.xAxis.enabled = false
        
        
        return chart
    }
    
    // this func is required to conform to UIViewRepresentable protocol
    func updateUIView(_ uiView: LineChartView, context: Context) {
        //when data changes chartd.data update is required
        uiView.data = addData()
    }
    
    func addData() -> LineChartData{
        let data = LineChartData()
        //BarChartDataSet is an object that contains information about your data, styling and more
        let dataSet = LineChartDataSet(entries: scoreEntries)
        let dataSet2 = LineChartDataSet(entries: averageEntries)
        // change bars color to green
        dataSet.colors = [NSUIColor.green]
        //change data label
        dataSet.label = "나의 점수"
        dataSet.circleColors = [.green]
        dataSet2.colors = [.blue]
        dataSet2.circleColors = [.blue]
        dataSet2.label = "평균 점수"
        
        data.addDataSet(dataSet)
        data.addDataSet(dataSet2)
        return data
    }
    
    typealias UIViewType = LineChartView
    
}



struct Bar_Previews: PreviewProvider {
    static var previews: some View {
        TestChartView (scoreEntries: [
            ChartDataEntry(x: 0, y: 80),
            ChartDataEntry(x: 1, y: 70),
            ChartDataEntry(x: 2, y: 90),
            ChartDataEntry(x: 3, y: 100),
            ChartDataEntry(x: 4, y: 60)

        ], averageEntries:  [
            ChartDataEntry(x: 0, y: 60),
            ChartDataEntry(x: 1, y: 40),
            ChartDataEntry(x: 2, y: 50),
            ChartDataEntry(x: 3, y: 30),
            ChartDataEntry(x: 4, y: 70)

        ])
    }
}
