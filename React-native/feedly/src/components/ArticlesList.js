import React from 'react';
import { View, Text, FlatList, Button } from 'react-native';
import { getArticlesList } from '../repository/ArticleRepo';
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
        navigate('Details', { oldtitle: itemtitle, title: itemtitle, author: itemauthor, description: itemdescription, topic: itemtopic });
    }

    _onPressButton = () => {
        const { navigate } = this.props.navigation;
        navigate('Details');
    };

    getListData() {
        return getArticlesList();
    }

    render() {
        const articleList = this.getListData();
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