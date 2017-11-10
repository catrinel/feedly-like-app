import React from 'react';
import { Navigator } from 'react-native';
import { ArticlesList } from './src/components/ArticlesList';
import { ArticleDetail } from './src/components/ArticleDetail';
import {
  StackNavigator,
} from 'react-navigation';


const AppNavigator = StackNavigator({
  Main: {screen: ArticlesList},
  Details: {screen: ArticleDetail},
});


export default class App extends React.Component {
  render() {
    return (
      <AppNavigator/>
    );
  }
}
