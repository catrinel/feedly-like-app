import React from 'react';
import { Navigator, AsyncStorage } from 'react-native';
import { ArticlesList } from './src/components/ArticlesList';
import { ArticleDetail } from './src/components/ArticleDetail';
import {
  StackNavigator,
} from 'react-navigation';
import {getArticlesList} from './src/repository/ArticleRepo';


const AppNavigator = StackNavigator({
  Main: {screen: ArticlesList},
  Details: {screen: ArticleDetail},
});


export default class App extends React.Component {

  componentWillMount(){
    this._populateList();
  }

  async _populateList(){
    await AsyncStorage.setItem('articleList', JSON.stringify(getArticlesList()));
  }

  render() {
    return (
      <AppNavigator/>
    );
  }
}
