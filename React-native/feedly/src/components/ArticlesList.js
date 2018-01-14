import React from 'react';
import * as firebase from 'firebase';
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
            list : [],
            userRole: ''
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
            rating={item.rating}
            history={item.history}
            onPress={ () => {this._onItemPressed(item);} }
        />
    );

    _onItemPressed(item){
        const { navigate } = this.props.navigation;
        navigate('Details', { item, isAdd: false});
    }

    _onPressButton = () => {
        const { navigate } = this.props.navigation;
        const item = {
            title: '',
            author: '',
            history: '1',
            description: '',
            topic: '',
            id: 0,
            rating: 1
        }
        navigate('Details',  {item, isAdd: true});
    };

    async getListData() {
        firebase.database().ref().child('article').on('value', (snapshot) => {
			const list = [];
			snapshot.forEach((article) => {
				list.push({ 
					...article.val(), 
					id: article.key
				})
			});
			AsyncStorage.getItem('userRole').then((userRole) => {
				this.setState({list, userRole});
			});
		});
    }

    logout = async () => {
		try {
			await AsyncStorage.clear(); 
			const {navigate} = this.props.navigation;
			navigate('Main');
	
		} catch (error) {
			console.log(error);
		}
	}

    render() {
        const articleList = this.state.list;

        const addButton = this.state.userRole === 'ADMIN' && (
            <Button
                onPress={this._onPressButton}
                title="   +   "
                color="#bada55"
            />
        );

        return (
            <View style={{alignItems: 'center'}}>
                <Button
					onPress={() => {this.logout();}}
                    title={"Logout"}
                    color='crimson'
				/>

                {addButton}
                
                <FlatList
                    data={articleList}
                    renderItem={this._renderItem}
                    keyExtractor={this._keyExtractor}
                />
            </View>
        );
    }
}