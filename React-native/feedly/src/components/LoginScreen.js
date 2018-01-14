import * as React from 'react';
import * as firebase from 'firebase';
import {AsyncStorage} from 'react-native';
import { Text, View, Button, TextInput } from 'react-native';

export class LoginScreen extends React.Component {
    static navigationOptions = {
		title: "Login",
	};

    constructor(props) {
        super(props);
        this.state = {
            email: '',
            password: ''
        }
    }

    render() {
        return (
            <View>
				<Text style={{fontSize: 15, padding: 5}}>
                    Email:
                </Text>
                <TextInput
                    style={{fontSize: 18, padding: 10}}
                    value={this.state.email}
                    onChangeText={(email) => this.setState({email})}
                />
                <Text style={{fontSize: 15, padding: 5}}>
                    Password:
                </Text>
                <TextInput
                    style={{fontSize: 18, padding: 10}}
                    value={this.state.password}
                    onChangeText={(password) => this.setState({password})}
                />
                <Button
                    onPress={() => this.login(this.state.email, this.state.password)}
                    title={"Sign in"}
                    color="#bada55"
                />
			</View>
        );
    }

    async login(email, pass) {
        
        try {
            const { uid } = await firebase.auth()
                .signInWithEmailAndPassword(email, pass);
            
            firebase.database().ref().child('role').child(uid).once('value')
            .then((snapshot) => {
                const role = snapshot.val();
                
                if (role === 'USER' || role === 'ADMIN') {
                    AsyncStorage.setItem('userRole', role).then(() => {
                        const {navigate} = this.props.navigation;
                        navigate('List');
                    });
                } else {
                    console.log("error");
                }
            });
                    

        } catch (error) {
            console.log(error.toString())
        }
    }
}