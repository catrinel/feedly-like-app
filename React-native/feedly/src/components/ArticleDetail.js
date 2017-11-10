import React from 'react';
import { View, Text, FlatList, TextInput, Button } from 'react-native';
import { editArticle, addArticle } from '../repository/ArticleRepo';
import Communications from 'react-native-communications';

export class ArticleDetail extends React.Component {
    static navigationOptions = {
        title: 'Article Details',
    };

    constructor(props) {
        super(props);
        const pr = this.props.navigation.state.params;

        if(pr.title !== ''){
            this.state = {
                oldtitle: pr.oldtitle,
                title: pr.oldtitle,
                description: pr.description,
                author: pr.author,
                topic: pr.topic,
                isAdd: false
            };
        }
        else{
            this.state = {
                oldtitle: '',
                title: '',
                description: '',
                author: '',
                topic: '',
                isAdd: true
            };
        }
    }

    _onPressButton = () => {
        if (this.state.isAdd){
            addArticle(this.state.title, this.state.author, this.state.description, this.state.topic);
            Communications.email(['tipitza@gmail.com'], null, null, 'New article: ' + this.state.title, this.state.author + ' added a new article!');
        }
        else{
            editArticle(this.state.oldtitle, this.state.title, this.state.description, this.state.topic);
        }
        const { navigate } = this.props.navigation;
        navigate('Main');
    };
    
    render() {
        const { isAdd } = this.state;
        const buttonText = isAdd ? "Add" : "Save";

        return (
            <View>
                <Text style={{fontSize: 20, padding: 10}}>{"Title"}</Text>
                <TextInput style={{fontSize: 22, padding: 10}}
                    onChangeText={(title) => this.setState({title})}
                    value={this.state.title}
                ></TextInput>
                <Text style={{fontSize: 20, padding: 10}}>{"Author"}</Text>
                <TextInput style={{fontSize: 22, padding: 10}}
                    onChangeText={(author) => this.setState({author})}
                    value={this.state.author}
                >
                </TextInput>
                <Text style={{fontSize: 20, padding: 10}}>{"Topic"}</Text>
                <TextInput style={{fontSize: 22, padding: 10}}
                    onChangeText={(topic) => this.setState({topic})}
                    value={this.state.topic}
                ></TextInput>
                <Text style={{fontSize: 20, padding: 10}}>{"Description"}</Text>
                <TextInput style={{fontSize: 22, padding: 10, marginBottom: 50}}
                    onChangeText={(description) => this.setState({description})}
                    value={this.state.description}
                ></TextInput>
               
                <Button
                    onPress={this._onPressButton}
                    title={buttonText}
                    color="#bada55"
                />
            </View>
        );
    }
}