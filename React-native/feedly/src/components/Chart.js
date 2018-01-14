import React from 'react';
import { View, Text, StyleSheet } from 'react-native';

import { Bar } from 'react-native-pathjs-charts'

export class ViewsChart extends React.Component {

  constructor(props){
    super(props);
}

render() {
  if (this.props.list.length === 0){
    return (<Text>No rating history</Text>);
  }

  let chartData = this.props.list.map(rating => {
      return {
          "v" : rating,
          "name": ""
      };
  });

  chartData[0].name = "rating";

  let data = [];
  data.push(chartData);
  
    let options = {
      width: 300,
      height: 300,
      margin: {
        top: 20,
        left: 25,
        bottom: 50,
        right: 20
      },
      color: '#2980B9',
      gutter: 20,
      animate: {
        type: 'oneByOne',
        duration: 200,
        fillTransition: 3
      },
      axisX: {
        showAxis: true,
        showLines: true,
        showLabels: true,
        showTicks: true,
        zeroAxis: false,
        orient: 'bottom',
        label: {
          fontFamily: 'Arial',
          fontSize: 8,
          fontWeight: true,
          fill: '#34495E'
        }
      },
      axisY: {
        showAxis: true,
        showLines: true,
        showLabels: true,
        showTicks: true,
        zeroAxis: false,
        orient: 'left',
        label: {
          fontFamily: 'Arial',
          fontSize: 8,
          fontWeight: true,
          fill: '#34495E'
        }
      }
    }
  
    return (
      <View>
        <Bar data={data} options={options} accessorKey='v'/>
      </View>
    );
  }
}