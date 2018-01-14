import React from 'react';
import { Navigator, AsyncStorage } from 'react-native';
import { ArticlesList } from './src/components/ArticlesList';
import { ArticleDetail } from './src/components/ArticleDetail';
import { StackNavigator } from 'react-navigation';
import * as firebase from 'firebase';
import { LoginScreen } from './src/components/LoginScreen';

var config = {
  apiKey: "AIzaSyCQk3TNarW0PuOurIL-ISeyt_4XmVZEz_s",
  authDomain: "feddlike-c0b5f.firebaseapp.com",
  databaseURL: "https://feddlike-c0b5f.firebaseio.com",
  projectId: "feddlike-c0b5f",
  storageBucket: "feddlike-c0b5f.appspot.com",
  messagingSenderId: "987721433574"
};

const AppNavigator = StackNavigator({
  Main: {screen: LoginScreen},
  List: {screen: ArticlesList},
  Details: {screen: ArticleDetail},
});


export default class App extends React.Component {

  componentWillMount(){
    firebase.initializeApp(config);
    console.disableYellowBox = true;
  }

  render() {
    return (
      <AppNavigator/>
    );
  }
}
