import React from 'react';
import withLayout from '../components/layout';
import axios from 'axios';
import jwt from 'jsonwebtoken';

class SignUp extends React.Component {

    state = {
        name: '',
        email: '',
        password: '',
    }

    handleChange = (e) => {
        const target = e.target;
        const value = target.value;
        const name = target.name;

        this.setState({
            [name]: value
        })
    }

    handleSubmit = (e) => {
        e.preventDefault();

        const { name, email, password } = this.state;

        axios.post('http://localhost:3001/api/signup', {
            name: name,
            email: email,
            isAdmin: false,
            password: password,
            authenticated: false
          })
        .then((response) => {
            if (response.data) {
                let token =jwt.sign({ 
                        name: response.data.name
                    }, 'ImaDamith');

                window.localStorage.setItem('token', token);
            }
        })
        .catch(function (error) {
            console.log(error);
        });

    }

    render () {
        const { userName, password } = this.state;

        return (
            <div className="container">
                <h2 className="title">Sign Up</h2>
                <form onSubmit={this.handleSubmit}>
                    <ul className="form">
                        <li>
                            <input type='text' name='name' onChange={this.handleChange} value={userName} placeholder='Name' />
                        </li>
                        <li>
                            <input type='text' name='email' onChange={this.handleChange} value={userName} placeholder='Email' />
                        </li>
                        <li>
                            <input type='password' name='password' onChange={this.handleChange} value={password} placeholder='Password' />
                        </li>
                        <li>
                            <input className="submitButton" type="submit" value="Sign Up" />
                        </li>
                    </ul>
                </form>
                <style jsx>{`
                    .container {
                        position: absolute;
                        width: 300px;
                        height: 200px;
                        z-index: 15;
                        top: 30%;
                        left: 50%;
                        margin: -100px 0 0 -150px;
                    }

                    .container * {
                        box-sizing: border-box;
                    }

                    .container li input {
                        width: 220px;
                        padding: 10px;
                        margin-bottom: 10px;

                    }

                    .container li .submitButton {
                        margin-left: auto;
                        padding: 8px 16px;
                        border: none;
                        background: #067df7;
                        color: #f2f2f2;
                        text-transform: uppercase;
                        letter-spacing: .09em;
                        border-radius: 2px;
                    }

                    .form {
                        list-style-type: none;
                        padding: 0;
                    }
                `}</style>
            </div>
        )
    }
}

export default withLayout(SignUp);