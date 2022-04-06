package com.example.suitgamejavaversion.main;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.suitgamejavaversion.R;
import com.example.suitgamejavaversion.databinding.ActivityMainBinding;
import com.example.suitgamejavaversion.utils.GetWinner;
import com.example.suitgamejavaversion.utils.Hands;

import java.util.Objects;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    Hands playerOneHand;
    Hands computerHand;
    int playerScore = 0;
    int computerScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        viewBinding();
        dialogShow();
    }

    private void viewBinding() {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setOnClickListeners();
    }

    private void dialogShow() {
        View instructions = getLayoutInflater().inflate(R.layout.instructions_dialog_fragment, null);

        AlertDialog.Builder dialog = new AlertDialog.Builder(this)
                .setView(instructions);
        AlertDialog dialogShow = dialog.show();

        TextView dismiss = instructions.findViewById(R.id.tv_got_it);
        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogShow.dismiss();
            }
        });


    }

    private void setOnClickListeners() {
            binding.flRock.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    binding.flRock.setBackgroundResource(R.drawable.bg_selected);
                    playerOneHand = Hands.ROCK;
                    binding.ivPlayerHand.setImageResource(R.drawable.batu);
                    binding.ivPlayerHand.setRotation(90F);
                    binding.flPaper.setBackgroundResource(R.drawable.bg_bottom_right_shadow);
                    binding.flScissors.setBackgroundResource(R.drawable.bg_bottom_right_shadow);

                    getComputerHand();
                    getWinner();
                    showWinnerDialog();
                }
            });
            binding.flPaper.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    binding.flRock.setBackgroundResource(R.drawable.bg_bottom_right_shadow);
                    binding.flPaper.setBackgroundResource(R.drawable.bg_selected);
                    playerOneHand = Hands.PAPER;
                    binding.ivPlayerHand.setImageResource(R.drawable.kertas);
                    binding.ivPlayerHand.setRotation(90F);
                    binding.flScissors.setBackgroundResource(R.drawable.bg_bottom_right_shadow);

                    getComputerHand();
                    getWinner();
                    showWinnerDialog();
                }
            });
            binding.flScissors.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    binding.flRock.setBackgroundResource(R.drawable.bg_bottom_right_shadow);
                    binding.flPaper.setBackgroundResource(R.drawable.bg_bottom_right_shadow);
                    binding.flScissors.setBackgroundResource(R.drawable.bg_selected);
                    playerOneHand = Hands.SCISSORS;
                    binding.ivPlayerHand.setImageResource(R.drawable.gunting);
                    binding.ivPlayerHand.setRotation(110F);

                    getComputerHand();
                    getWinner();
                    showWinnerDialog();
                }
            });

        binding.ivShowInstructions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogShow();
            }
        });
    }

    private void getComputerHand() {
        Random random = new Random();
        int randomNumber = random.nextInt(2) + 1;
        computerHand = Hands.values()[randomNumber];
        switch (computerHand) {
            case ROCK:
                binding.ivComHand.setImageResource(R.drawable.batu);
                binding.ivComHand.setRotation(90F);
                binding.ivComHand.setRotationY(180F);
                break;
            case PAPER:
                binding.ivComHand.setImageResource(R.drawable.kertas);
                binding.ivComHand.setRotation(90F);
                binding.ivComHand.setRotationY(180F);
                break;
            case SCISSORS:
                binding.ivComHand.setImageResource(R.drawable.gunting);
                binding.ivComHand.setRotation(110F);
                binding.ivComHand.setRotationY(180F);
                break;
        }
    }

    private void getWinner() {
        if (GetWinner.getWinner(playerOneHand, computerHand).equals(GetWinner.DRAW)) {
            binding.tvVs.setText(GetWinner.DRAW);
        } else if (GetWinner.getWinner(playerOneHand, computerHand).equals(GetWinner.PLAYER_ONE_WIN)) {
            binding.tvVs.setText(GetWinner.PLAYER_ONE_WIN);
            playerScore++;
            binding.tvPlayerScore.setText(String.valueOf(playerScore));
        } else {
            binding.tvVs.setText(GetWinner.COMPUTER_WIN);
            computerScore++;
            binding.tvComScore.setText(String.valueOf(computerScore));
        }
    }

    private void showWinnerDialog() {
        View playerWins = getLayoutInflater().inflate(R.layout.dialog_player_wins, null);
        View comWins = getLayoutInflater().inflate(R.layout.dialog_computer_wins, null);
        View dismissDialogPlayer = playerWins.findViewById(R.id.tv_dismiss_dialog);
        View dismissDialogCom = comWins.findViewById(R.id.tv_dismiss_dialog);
        View playAgainPlayer = playerWins.findViewById(R.id.tv_play_again);
        View playAgainCom = comWins.findViewById(R.id.tv_play_again);

        if (playerScore == 3) {
            binding.flRock.setEnabled(false);
            binding.flPaper.setEnabled(false);
            binding.flScissors.setEnabled(false);
            AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
            dialog.setView(playerWins);
            AlertDialog showDialog = dialog.show();
            dismissDialogPlayer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showDialog.dismiss();
                }
            });
            playAgainPlayer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewBinding();
                    playerScore = 0;
                    computerScore = 0;
                    binding.flRock.setEnabled(true);
                    binding.flPaper.setEnabled(true);
                    binding.flScissors.setEnabled(true);
                    Toast.makeText(MainActivity.this, "Game Reset Successful!", Toast.LENGTH_SHORT).show();
                    showDialog.dismiss();
                }
            });

        } else if (computerScore == 3) {
            binding.flRock.setEnabled(false);
            binding.flPaper.setEnabled(false);
            binding.flScissors.setEnabled(false);
            AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
            dialog.setView(comWins);
            AlertDialog showDialog = dialog.show();
            dismissDialogCom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showDialog.dismiss();
                }
            });
            playAgainCom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewBinding();
                    playerScore = 0;
                    computerScore = 0;
                    binding.flRock.setEnabled(true);
                    binding.flPaper.setEnabled(true);
                    binding.flScissors.setEnabled(true);
                    Toast.makeText(MainActivity.this, "Game Reset Successful!", Toast.LENGTH_SHORT).show();
                    showDialog.dismiss();
                }
            });
        }

    }

}