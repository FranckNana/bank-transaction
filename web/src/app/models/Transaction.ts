export class Transaction {
  primaryId!: string;
  secondaryId!: string;
  eventType!: string;
  eventDateTime?: Date | null;
  stepRank!: number;
  eventRank!: number;
}