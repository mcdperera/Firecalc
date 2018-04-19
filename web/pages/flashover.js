import React from 'react';
import Link from 'next/link';
import withLayout from '../components/layout';
import axios from 'axios';

class FlashOver extends React.Component {

        state = {
            compartmentWidth: '',
            compartmentLength: '',
            compartmentHeight: '',
            ventWidth: '',
            ventHeight: '',
            ilt: '',
            compartmentWidthMu: '0',
            compartmentLengthMu: '0',
            compartmentHeightMu: '0',
            ventWidthMu: '0',
            ventHeightMu: '0',
            iltMu: '0',
        }

        openModal = () => {
            this.setState({modalIsOpen: true});
        }

        afterOpenModal = () => {
            // references are now sync'd and can be accessed.
            this.subtitle.style.color = '#f00';
        }

        closeModal = () => {
            this.setState({modalIsOpen: false});
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
            const {
                compartmentWidth,
                compartmentLength,
                compartmentHeight,
                ventWidth,
                ventHeight,
                ilt,
                compartmentWidthMu,
                compartmentLengthMu,
                compartmentHeightMu,
                ventWidthMu,
                ventHeightMu,
                iltMu,
             } = this.state;

            axios.post('http://localhost:3001/api/flashover', {
                CompartmentWidth: compartmentWidth,
                CompartmentWidthMeasure: compartmentWidthMu,
                CompartmentLength: compartmentLength,
                CompartmentLengthMeasure: compartmentLengthMu,
                CompartmentHeight: compartmentHeight,
                CompartmentHeightMeasure: compartmentHeightMu,
                VentWidth: ventWidth,
                VentWidthMeasure: ventWidthMu,
                VentHeight: ventHeight,
                VentHeightMeasure: ventHeightMu,
                InteriorLiningThickness: ilt,
                InteriorLiningThicknessMeasure: iltMu,
            })
            .then((response) => {
                console.log(response);
            })
            .catch(function (error) {
                console.log(error);
            });

        }

        clearFields = () => {
            this.setState({
                compartmentWidth: '',
                compartmentLength: '',
                compartmentHeight: '',
                ventWidth: '',
                ventHeight: '',
                ilt: '',
                compartmentWidthMu: '0',
                compartmentLengthMu: '0',
                compartmentHeightMu: '0',
                ventWidthMu: '0',
                ventHeightMu: '0',
                iltMu: '0',
            });
        }
    
        render() {
            const {
                compartmentWidth,
                compartmentLength,
                compartmentHeight,
                ventWidth,
                ventHeight,
                ilt,
             } = this.state;

            return (
                <div className="container">
                    <h2 className="title">Flashover</h2>
                    <form onSubmit={this.handleSubmit}>
                        <ul className="form">
                            <li>
                                <input type='text' name='compartmentWidth' onChange={this.handleChange} value={compartmentWidth} placeholder='Compartment Width' />
                                <select name='compartmentWidthMu' value={this.state.value} onChange={this.handleChange}>
                                    <option value="0">cm</option>
                                    <option value="1">feet</option>
                                    <option value="2">inches</option>
                                    <option value="3">meters</option>
                                    <option value="4">mm</option>
                                </select>
                            </li>
                            <li>
                                <input type='text' name='compartmentLength' onChange={this.handleChange} value={compartmentLength} placeholder='Compartment Length' />
                                <select name='compartmentLengthMu' value={this.state.value} onChange={this.handleChange}>
                                    <option value="0">cm</option>
                                    <option value="1">feet</option>
                                    <option value="2">inches</option>
                                    <option value="3">meters</option>
                                    <option value="4">mm</option>
                                </select>
                            </li>
                            <li>
                                <input type='text' name='compartmentHeight' onChange={this.handleChange} value={compartmentHeight} placeholder='Compartment Height' />
                                <select name='compartmentHeightMu' value={this.state.value} onChange={this.handleChange}>
                                    <option value="0">cm</option>
                                    <option value="1">feet</option>
                                    <option value="2">inches</option>
                                    <option value="3">meters</option>
                                    <option value="4">mm</option>
                                </select>
                            </li>
                            <li>
                                <input type='text' name='ventWidth' onChange={this.handleChange} value={ventWidth} placeholder='Vent Width' />
                                <select name='ventWidthMu' value={this.state.value} onChange={this.handleChange}>
                                    <option value="0">cm</option>
                                    <option value="1">feet</option>
                                    <option value="2">inches</option>
                                    <option value="3">meters</option>
                                    <option value="4">mm</option>
                                </select>
                            </li>
                            <li>
                                <input type='text' name='ventHeight' onChange={this.handleChange} value={ventHeight} placeholder='Vent Height' />
                                <select name='ventHeightMu' value={this.state.value} onChange={this.handleChange}>
                                    <option value="0">cm</option>
                                    <option value="1">feet</option>
                                    <option value="2">inches</option>
                                    <option value="3">meters</option>
                                    <option value="4">mm</option>
                                </select>
                            </li>
                            <li>
                                <input type='text' name='ilt' onChange={this.handleChange} value={ilt} placeholder='Interior Lining Thickness' />
                                <select name='iltMu' value={this.state.value} onChange={this.handleChange}>
                                    <option value="0">cm</option>
                                    <option value="1">feet</option>
                                    <option value="2">inches</option>
                                    <option value="3">meters</option>
                                    <option value="4">mm</option>
                                </select>
                            </li>
                            <li>
                                <input className="submitButton" type="submit" value="Calculate" />
                            </li>
                            <li>
                                <button className="submitButton"  onClick={this.clearFields} >
                                    Clear
                                </button>
                            </li>
                            <br/>
                            <li>
                                <button className="submitButton"  onClick={this.clearFields} >
                                    Patrice
                                </button>
                            </li>
                            <br/>
                            <li>
                                <button className="submitButton"  onClick={this.clearFields} >
                                    Reference
                                </button>
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

                        select {
                            height: 35px;
                            width: 65px;
                            margin-left: 12px;
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

export default withLayout(FlashOver);