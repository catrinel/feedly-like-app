import React from 'react';
import { View, Text, FlatList, Button, AsyncStorage } from 'react-native';
import { ArticleDetail } from './ArticleDetail';

class ArticleListItem extends React.PureComponent {
    render() {
      return (
        <Text onPress={this.props.onPress} style={{fontSize: 20, padding: 20 }}>
            {this.props.title}
            <Text style={{color: '#bada55'}}>{ " by " + this.props.author} </Text>
        </Text>
      )
    }
};

export class ArticlesList extends React.Component {
    static navigationOptions = {
        title: 'Articles list',
    };

    constructor(props) {
        super(props);
        this.state = {
            list : []
        }
      }

    componentDidMount(){
        this.getListData();
    }

    _keyExtractor = (item, index) => item.title;

    _renderItem = ({item}) => (
        <ArticleListItem
            selected={item}
            title={item.title}
            author={item.author}
            description={item.description}
            topic={item.topic}
            onPress={ () => {this._onItemPressed(item.title, item.author, item.description, item.topic);} }
        />
    );

    _onItemPressed(itemtitle, itemauthor, itemdescription, itemtopic){
        const { navigate } = this.props.navigation;
        navigate('Details', { oldtitle: itemtitle, title: itemtitle, author: itemauthor, description: itemdescription, topic: itemtopic, isAdd: false});
    }

    _onPressButton = () => {
        const { navigate } = this.props.navigation;
        navigate('Details',  {oldtitle: '', title: '', author: '', description: '', topic: '', isAdd: true});
    };

    async getListData() {
        let response = await AsyncStorage.getItem('articleList'); 
        let articleList = await JSON.parse(response); 

        if(!this._arraysEqual(this.state.list, articleList)){
            this.setState({list: articleList});
        }

    }

    _arraysEqual(array1, array2){
        if (array1.length !== array2.length){
            return false;
        }

        for(let i = 0; i < array1.length; i++){
            if (! this._articleEqual(array1[i], array2[i])){
                return false;
            }
        }

        return true;
    }

    _articleEqual(art1, art2){
        return art1.title === art2.title &&
            art1.author === art2.author &&
            art1.description === art2.description &&
            art1.topic === art2.topic;
    }

    render() {
        this.getListData();
        const articleList = this.state.list;

        return (
            <View style={{alignItems: 'center'}}>
                <Button
                    onPress={this._onPressButton}
                    title="   +   "
                    color="#bada55"
                />
                <FlatList
                    data={articleList}
                    renderItem={this._renderItem}
                    keyExtractor={this._keyExtractor}
                />
            </View>
        );
    }
}