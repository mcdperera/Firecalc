import React from 'react';
import Head from './head';
import Nav from './nav';

export default (ComposedComponent) => (
    class Layout extends React.Component {

        state = {
            token: '',
        }

        componentDidMount() {
            let token = window.localStorage.getItem('token');
            if (token) {
                this.setState({
                    token: token
                })
            }
        }

        signOut = () => {
            console.log('YEEES');
            localStorage.clear();
            this.setState({
                token: ''
            })
        }

        render () {
            const { token } = this.state;
            return (
                <div>
                    <Head title="Home" />
                    <Nav token={token} signOut={this.signOut}/>
                    <ComposedComponent />
                </div>
            )
        }
    }
);