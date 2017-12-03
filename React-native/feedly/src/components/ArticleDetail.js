import React from 'react';
import { View, Text, FlatList, TextInput, Button, Alert, Picker } from 'react-native';
import { editArticle, addArticle, deleteArticle } from '../repository/ArticleRepo';
import PopupDialog, { DialogTitle } from 'react-native-popup-dialog';
import Communications from 'react-native-communications';

export class ArticleDetail extends React.Component {
    static navigationOptions = {
        title: 'Article Details',
    };

    constructor(props) {
        super(props);
        const pr = this.props.navigation.state.params;
        
        this.state = {
            oldtitle: pr.oldtitle,
            title: pr.oldtitle,
            description: pr.description,
            author: pr.author,
            topic: pr.topic,
            isAdd: pr.isAdd
        };
    }

    _onPressButton = () => {
        if (this.state.isAdd){
            addArticle(this.state.title, this.state.author, this.state.description, this.state.topic);
            Communications.email(['tipitza@gmail.com'], null, null, 'New article: ' + this.state.title, this.state.author + ' added a new article!');
        }
        else{
            editArticle(this.state.oldtitle, this.state.title, this.state.description, this.state.topic, this.state.author);
        }
        const { navigate } = this.props.navigation;
        navigate('Main');
    };
    
    _delete = () => {
        deleteArticle(this.state.oldtitle);
        const { navigate } = this.props.navigation;
        navigate('Main');
    }

    render() {
        const { isAdd } = this.state;
        const buttonText = isAdd ? "Add" : "Save";
        const deleteButton = !isAdd && 
            <Button
                onPress={() => {
                    Alert.alert(
                        'Are you sure you want to delete this article?',
                        'This operation is not reversible.',
                        [
                          {text: 'Cancel', onPress: () => {}},
                          {text: 'OK', onPress: () => {this._delete();}},
                        ],
                        { cancelable: false }
                      );
                }}
                title="Delete"
                color="#C70039"
            />
        ;

        return (
            <View>
                <Text style={{fontSize: 20, padding: 10}}>{"Title"}</Text>
                <TextInput style={{fontSize: 22, padding: 10}}
                    onChangeText={(title) => this.setState({title})}
                    value={this.state.title}
                ></TextInput>
                <Text style={{fontSize: 20, padding: 10}}>{"Author"}</Text>
                <Picker
                    selectedValue={this.state.author}
                    onValueChange={(itemValue, itemIndex) => {
                        console.log(itemValue, itemIndex);
                        this.setState({author: itemValue});
                    }}>
                    <Picker.Item label="--- Select author ---" value="none" />
                    <Picker.Item label="Bogdi" value="Bogdi" />
                    <Picker.Item label="Catri" value="Catri" />
                    <Picker.Item label="PS Blog" value="PS Blog" />
                    <Picker.Item label="Riot" value="Riot" />
                </Picker>

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

                {deleteButton}

            </View>
        );
    }
}