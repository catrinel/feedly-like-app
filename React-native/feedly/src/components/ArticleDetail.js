import React from 'react';
import { View, Text, FlatList, TextInput, Button, Alert, Picker, StyleSheet, AsyncStorage } from 'react-native';
import Communications from 'react-native-communications';
import {ViewsChart} from './Chart';
import * as firebase from 'firebase';
import Modal from 'react-native-modal';

const styles = StyleSheet.create({
    modalContent: {
      backgroundColor: 'white',
      padding: 22,
      justifyContent: 'center',
      alignItems: 'center',
      borderRadius: 4,
      borderColor: 'rgba(0, 0, 0, 0.1)',
    }
});

export class ArticleDetail extends React.Component {
    static navigationOptions = {
        title: 'Article Details',
    };

    constructor(props) {
        super(props);
        const item = this.props.navigation.state.params.item;
        
        this.state = {
            id: item.id,
            title: item.title,
            author: item.author,
            description: item.description,
            history: item.history,
            topic: item.topic,
            rating: JSON.stringify(item.rating),
            isAdd: this.props.navigation.state.params.isAdd,
            isModalVisible: false,
            userRole: ''
        };
    }

    componentDidMount() {
        AsyncStorage.getItem('userRole').then((userRole) => {
            this.setState({userRole});
        });
    }

    _onPressButton = () => {
        this.state.isAdd ? this.addArticle() : this.updateArticle();
        
    };

    addArticle() {
        var newKey = firebase.database().ref().child('article').push().key;
		
		firebase.database().ref().child('article').child(newKey).set({
            title: this.state.title,
            author: this.state.author, 
            description: this.state.description, 
            topic: this.state.topic, 
            rating: parseFloat(this.state.rating),
			id: newKey,
			history: this.state.rating
          })
        .then(() => {
            Communications
                .email(['tipitza@gmail.com'], null, null, 'New article: ' + this.state.title, this.state.author + ' added a new article!');
            const { navigate } = this.props.navigation;
            navigate('List');
        });
    }

    updateArticle() {
        firebase.database().ref().child('article').child(this.state.id).set({
            title: this.state.title,
            author: this.state.author, 
            description: this.state.description, 
            topic: this.state.topic, 
            rating: parseFloat(this.state.rating),
			id: this.state.id,
			history: this.state.history + ';' + this.state.rating
          })
        .then(() => {
            const { navigate } = this.props.navigation;
            navigate('List');
        });
    }

    _delete() {
        firebase.database().ref().child('medicine').child(this.state.id)
        .remove()
        .then(() => {
            const { navigate } = this.props.navigation;
            navigate('List');
        });
    }

    formatHistory(history) {
        let result = this.state.history.split(';');
        return result = result.map(h => parseFloat(h));
	}

    render() {
        const { isAdd } = this.state;
        const buttonText = isAdd ? "Add" : "Save";
        const deleteButton = !isAdd && this.state.userRole === 'ADMIN' && (
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
        );

        const saveButton = this.state.userRole === 'ADMIN' && (
            <Button
                onPress={this._onPressButton}
                title={buttonText}
                color="#bada55"
            />
        );

        const chartButton = !isAdd && 
            <Button
                onPress={() => {
                this.setState({isModalVisible: true})
            }}
                title="View chart"
                color="#3498DB"
            />

        return (
            <View>
                <Text style={{fontSize: 15, padding: 5}}>{"Title"}</Text>
                <TextInput style={{fontSize: 18, padding: 10}}
                    onChangeText={(title) => this.setState({title})}
                    value={this.state.title}
                ></TextInput>
                <Text style={{fontSize: 15, padding: 5}}>{"Author"}</Text>
                <Picker
                    selectedValue={this.state.author}
                    onValueChange={(itemValue, itemIndex) => {
                        this.setState({author: itemValue});
                    }}>
                    <Picker.Item label="--- Select author ---" value="none" />
                    <Picker.Item label="Bogdi" value="Bogdi" />
                    <Picker.Item label="Catri" value="Catri" />
                    <Picker.Item label="PS Blog" value="PS Blog" />
                    <Picker.Item label="Riot" value="Riot" />
                    <Picker.Item label="fubiz" value="fubiz" />
                    <Picker.Item label="spring.io" value="spring.io" />
                </Picker>

                <Text style={{fontSize: 15, padding: 5}}>{"Topic"}</Text>
                <TextInput style={{fontSize: 18, padding: 10}}
                    onChangeText={(topic) => this.setState({topic})}
                    value={this.state.topic}
                ></TextInput>
                <Text style={{fontSize: 15, padding: 5}}>{"Description"}</Text>
                <TextInput style={{fontSize: 18, padding: 10, marginBottom: 30}}
                    onChangeText={(description) => this.setState({description})}
                    value={this.state.description}
                ></TextInput>

                <Text style={{fontSize: 15, padding: 5}}>{"Rating"}</Text>
                <TextInput style={{fontSize: 18, padding: 10}}
                    onChangeText={(rating) => this.setState({rating})}
                    value={this.state.rating}
                ></TextInput>
               
                {saveButton}

                {deleteButton}

                {chartButton}

                <Modal 
                    isVisible={this.state.isModalVisible}
                    animationIn={'slideInLeft'}
                    animationOut={'slideOutRight'}
                >
                    <View style={styles.modalContent}>  
                        <Text>Rating evolution</Text>
                        <ViewsChart list={this.formatHistory(this.state.history)}/>
                        <Button
                            onPress={() => {
                            this.setState({isModalVisible: false})
                        }}
                        title="Close"
                        />
                    </View>
                </Modal>
            </View>
        );
    }

}