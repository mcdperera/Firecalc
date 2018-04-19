import React from 'react';
import Link from 'next/link';
import withLayout from '../components/layout';
import axios from 'axios';

class Feedback extends React.Component {

        state = {
            title: '',
            desc: '',
            userId: '123123',
            allFeedback: []
        }

        componentDidMount () {
            axios.post('http://localhost:3001/api/feedbacks')
            .then((response) => {
                this.setState({
                    allFeedback: response.data
                })
                console.log(response);
            })
            .catch(function (error) {
                console.log(error);
            });
        }

     
        render() {

            const {
                title, 
                desc
            } = this.state;

            return (
                <div className="container">
                    <h2 className="title">Feedback</h2>

                    <table className="form">
                    <tr>
                        <th>
                            <label>
                                <b> Title</b>
                            </label>
                        </th>
                        <th>
                            <label>
                                <b>  Description</b>

                            </label>
                        </th>
                    </tr>

                    {this.state.allFeedback.map( feedback => (
                        <React.Fragment>
                        <tr>
                        <td>
                            <label>
                                {feedback.title}
                            </label>
                        </td>
                        <td>
                            <label>
                                {feedback.description}
                            </label>
                        </td>
                        </tr>
                        </React.Fragment>
                    ))}
                    </table>

                    <style jsx>{`
                        .container {
                            position: absolute;
                            width: 500px;
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

                        select {
                            height: 35px;
                            width: 65px;
                            margin-left: 12px;
                        }

                        .container li .submitButton  {
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

export default withLayout(Feedback);